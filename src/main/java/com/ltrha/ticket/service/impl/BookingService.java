package com.ltrha.ticket.service.impl;


import com.ltrha.ticket.config.security.UserPrincipal;
import com.ltrha.ticket.constant.business.TicketBookingStatusCode;
import com.ltrha.ticket.domain.mapper.TicketMapper;
import com.ltrha.ticket.domain.request.payment.BookingPaymentRequest;
import com.ltrha.ticket.domain.request.payment.GetPaymentUrlRequest;
import com.ltrha.ticket.domain.request.ticket.BookTicketRequest;
import com.ltrha.ticket.domain.request.ticket.CancelBookingRequest;
import com.ltrha.ticket.domain.request.ticket.CheckinRequest;
import com.ltrha.ticket.domain.response.ticket.BookTicketResponse;
import com.ltrha.ticket.event.booking.OnBookingFlightSuccessEvent;
import com.ltrha.ticket.event.booking.PaymentCompleteEvent;
import com.ltrha.ticket.exception.FlightTicketRuntimeException;
import com.ltrha.ticket.models.*;
import com.ltrha.ticket.repositories.*;
import com.ltrha.ticket.service.impl.client.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class BookingService {

    private final TicketRepository ticketRepository;
    private final FlightDetailRepository flightDetailRepository;
    private final FlightAirplaneSeatRepository flightAirplaneSeatRepository;
    private final FlightAirplaneClassDetailRepository flightAirplaneClassDetailRepository;
    private final AirplaneSeatOptionRepository airplaneSeatOptionRepository;
    //    private final UserRepository userRepository;
    private final UserServiceClient userServiceClient;
    private final MailService mailService;
    private final PaymentService paymentService;

    private final KafkaTemplate<String, Object> kafkaTemplate;


    private final ApplicationEventPublisher eventPublisher;
    //Mapper
    private final TicketMapper ticketMapper;

    public TicketEntity getTickerById(UUID id) {
        var ticket = ticketRepository
                .findById(id)
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Ticket not found"));

        return ticket;

    }


    @Transactional
    public BookTicketResponse bookTicket(BookTicketRequest request, UserPrincipal userPrincipal) {

        //Get current user
        var currentUser = userServiceClient.getUser(userPrincipal.getId().toString())
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Cannot find user"));

        //Create ticket owner
        TicketOwner tickerOwner = createTicketOwner(request);

        //Start booking flow
        int flightId = request.getFlightId();

        var flight = flightDetailRepository
                .findById(flightId)
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Cannot find flight"));


        var airplaneClassOption = airplaneSeatOptionRepository.findById(request.getAirplaneClassOptionId())
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Cannot find seat option"));

        if (airplaneClassOption.getAirplaneDetail().getId() != flight.getAirplane().getId()) {
            throw new FlightTicketRuntimeException(HttpStatus.BAD_REQUEST, "Seat option is not available for this flight");
        }
        var flightClassDetail = flightAirplaneClassDetailRepository.findByAirplaneClassOption_IdAndFlightDetail_Id(airplaneClassOption.getId(), flight.getId())
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Cannot find flight class detail"));


        //Decrease available seat and wait for payment status
        var availableSeat = flightClassDetail.getAvailableSeatCount();
        if (availableSeat <= 0) {
            throw new FlightTicketRuntimeException(HttpStatus.BAD_REQUEST, "No available seat");
        }

        flightClassDetail.setAvailableSeatCount(availableSeat - 1);
        airplaneSeatOptionRepository.save(airplaneClassOption);


        //Calculate fare
        long fare = flightClassDetail.getFare();


        //Create ticket
        TicketEntity ticket = TicketEntity.builder()
                .flightDetail(flight)
                .fare(fare)
                .owner(tickerOwner)
                .airplaneClassOption(airplaneClassOption)
                .status(1)
                .bookedById(currentUser.getId().toString())
                .status(TicketBookingStatusCode.PENDING)
                .build();
        try {
            ticketRepository.save(ticket);

        } catch (Exception e) {
            throw new FlightTicketRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "System error! Cannot book ticket");
        }


        //Return ticket info
        var createdTicket = ticketRepository.findById(ticket.getId())
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "System error! Cannot get ticket"));


        //Event
//        OnBookingFlightSuccessEvent onBookingFlightSuccessEvent = new OnBookingFlightSuccessEvent(createdTicket, currentUser);
//        eventPublisher.publishEvent(onBookingFlightSuccessEvent);


        return new BookTicketResponse(createdTicket.getId().toString(), createdTicket.getFare());


    }

    public String getBookingPaymentUrl(BookingPaymentRequest request) {
        TicketEntity ticket = ticketRepository.findById(UUID.fromString(request.getBookingId()))
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Cannot find ticket"));

        if(ticket.getStatus() != TicketBookingStatusCode.PENDING) {
            throw new FlightTicketRuntimeException(HttpStatus.BAD_REQUEST, "Ticket is not pending");
        }

        //Get payment url
        GetPaymentUrlRequest getPaymentUrlRequest = GetPaymentUrlRequest.builder()
                .paymentMethod(request.getPaymentMethod())
                .productType("ticket")
                .productId(ticket.getId().toString())
                .totalPrice(ticket.getFare())
                .paymentReturnUrl(request.getPaymentReturnUrl())
                .build();

        return paymentService.getPaymentUrl(getPaymentUrlRequest);


    }

    public void handleBookingPaymentCompleteEvent(PaymentCompleteEvent event) {
        //TODO: Handle payment fail event


        //Return ticket info
        var ticket = ticketRepository.findById(UUID.fromString(event.getBookingId()))
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "System error! Cannot get ticket"));

        var user = userServiceClient.getUser(ticket.getBookedById())
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Cannot find user"));


        if(ticket.getStatus() != TicketBookingStatusCode.PENDING) {
//            throw new FlightTicketRuntimeException(HttpStatus.BAD_REQUEST, "Ticket is not pending");
            //Do nothing
            return;
        }


        //Update ticket status
        ticket.setStatus(TicketBookingStatusCode.BOOKED);
        ticketRepository.saveAndFlush(ticket);

//        TicketResponse ticketResponse = ticketMapper.toTicketResponse(ticket, user);

        //Event
        OnBookingFlightSuccessEvent onBookingFlightSuccessEvent = new OnBookingFlightSuccessEvent(ticket.getId().toString(), user);
        eventPublisher.publishEvent(onBookingFlightSuccessEvent);


    }


    @Transactional
    public void checkin(CheckinRequest request) {
        //Verify user
        var ticket = ticketRepository
                .findById(request.getTicketId())
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Cannot find ticket"));

        if (ticket.getStatus() != TicketBookingStatusCode.BOOKED) {
            throw new FlightTicketRuntimeException(HttpStatus.BAD_REQUEST, "Ticket is not booked");
        }

        //Get seat
        var seatEntity = flightAirplaneSeatRepository
                .findFlightAirplaneSeatByFlight_IdAndAirplaneSeat_SeatCodeAndAirplaneSeat_AirplaneClassOption_Id
                        (ticket.getFlightDetail().getId(), request.getSeatCode(), ticket.getAirplaneClassOption().getId())
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Cannot find seat"));

        //Set ticket
        ticket.setSeat(seatEntity.getAirplaneSeat());
        ticket.setStatus(TicketBookingStatusCode.CHECKED_IN);
        ticketRepository.save(ticket);

        //Update seat status
        seatEntity.setAvailable(false);
        flightAirplaneSeatRepository.save(seatEntity);

    }

    @Transactional
    public void cancelTicket(CancelBookingRequest request, UserPrincipal userPrincipal) {
        var ticketId = request.getTicketId();


        if (userPrincipal == null) {
            throw new FlightTicketRuntimeException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        //Get ticket
        var ticket = ticketRepository
                .findByIdAndBookedById(ticketId, userPrincipal.getId().toString())
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Cannot find ticket"));


        if (ticket.getStatus() != TicketBookingStatusCode.BOOKED) {
            throw new FlightTicketRuntimeException(HttpStatus.BAD_REQUEST, "Ticket is not booked");
        }

        //Update ticket status
        ticket.setStatus(TicketBookingStatusCode.CANCELLED);
        ticketRepository.save(ticket);

        //Update seat status
        if (ticket.getSeat() != null) {
            var seat = flightAirplaneSeatRepository
                    .findFlightAirplaneSeatByFlight_IdAndAirplaneSeat_SeatCodeAndAirplaneSeat_AirplaneClassOption_Id
                            (ticket.getFlightDetail().getId(), ticket.getSeat().getSeatCode(), ticket.getAirplaneClassOption().getId())
                    .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Cannot find seat"));

            seat.setAvailable(true);
            flightAirplaneSeatRepository.save(seat);
        }

        //Update available seat
        var flightClassDetail = flightAirplaneClassDetailRepository.findByAirplaneClassOption_IdAndFlightDetail_Id(ticket.getAirplaneClassOption().getId(), ticket.getFlightDetail().getId())
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Cannot find flight class detail"));

        flightClassDetail.setAvailableSeatCount(flightClassDetail.getAvailableSeatCount() + 1);
        flightAirplaneClassDetailRepository.save(flightClassDetail);

    }

    private TicketOwner createTicketOwner(BookTicketRequest request) {
        return TicketOwner.builder()
                .name(request.getName())
                .dob(request.getDob())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .city(request.getCity())
                .country(request.getCountry())
                .identityCard(request.getIdentityCard())
                .build();
    }

    private TicketEntity generateTicket(UserEntity bookedBy, TicketOwner owner, AirplaneClassOption airplaneClassOption, AirplaneSeat seat, FlightDetailEntity flight, long fare) {

        TicketEntity ticket = new TicketEntity();
        ticket.setFlightDetail(flight);
        ticket.setFare(fare);
        ticket.setOwner(owner);
        ticket.setAirplaneClassOption(airplaneClassOption);
        ticket.setStatus(1);
        ticket.setSeat(seat);
        ticket.setBookedById(bookedBy.getId().toString());
        ticket.setStatus(TicketBookingStatusCode.BOOKED);
        return ticket;
    }


    public void produceSeatReservedEvent(String seatId) {
        kafkaTemplate.send("seat-reservation", "Seat reserved: " + seatId);
    }

    public void deleteTicket(UUID ticketId) {
        try {
            ticketRepository.deleteById(ticketId);
        } catch (Exception e) {
            throw new FlightTicketRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "System error! Cannot delete ticket");
        }
    }

//    public List<TicketResponse> getAllTickets() {
//        try {
//            var tickets = ticketRepository.findAll();
//
//            return tickets
//                    .stream()
//                    .map(
//                            ticketMapper::toTicketResponse
//                    )
//                    .toList();
//        } catch (Exception e) {
//            throw new FlightTicketRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "System error! Cannot get tickets");
//        }
//    }

}
