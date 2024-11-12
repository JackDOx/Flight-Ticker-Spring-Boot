package com.ltrha.ticket.schedule;

import com.ltrha.ticket.constant.business.TicketBookingStatusCode;
import com.ltrha.ticket.models.FlightAirplaneClassDetail;
import com.ltrha.ticket.models.FlightAirplaneSeat;
import com.ltrha.ticket.models.TicketEntity;
import com.ltrha.ticket.repositories.FlightAirplaneClassDetailRepository;
import com.ltrha.ticket.repositories.FlightAirplaneSeatRepository;
import com.ltrha.ticket.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingTask {

    private Logger logger = LoggerFactory.getLogger(BookingTask.class);

    private final TicketRepository ticketRepository;
    private final FlightAirplaneClassDetailRepository flightAirplaneClassDetailRepository;
    private final FlightAirplaneSeatRepository flightAirplaneSeatRepository;

    @Scheduled(fixedRateString = "${schedule-task.remove-cancelled-booking.fixed-rate}")
    @Transactional
    public void deleteCancelledBookingInDatabase() {
        ticketRepository.deleteByStatus(TicketBookingStatusCode.CANCELLED);
        logger.info("TASK COMPLETED: Deleted all cancelled booking in database");

    }

    @Scheduled(fixedRateString = "${schedule-task.remove-cancelled-booking.fixed-rate}")
    @Transactional
    public void deleteTimeoutPendingBookingInDatabase() {
        LocalDateTime expiredDate = LocalDateTime.now().minusMinutes(10);
        List<TicketEntity> expiredTicket = ticketRepository.findTicketWithCreatedDateBefore(expiredDate, TicketBookingStatusCode.PENDING);
        //Release seat
        expiredTicket.forEach(ticket -> {
            try {

                ticket.setStatus(TicketBookingStatusCode.CANCELLED);
                ticketRepository.save(ticket);

                //Set seat count
                FlightAirplaneClassDetail airplaneClass = flightAirplaneClassDetailRepository.findByAirplaneClassOption_IdAndFlightDetail_Id(ticket.getSeat().getAirplaneClassOption().getId(), ticket.getFlightDetail().getId())
                        .orElseThrow(() -> new RuntimeException("Airplane class not found"));
                airplaneClass.setAvailableSeatCount(airplaneClass.getAvailableSeatCount() + 1);
                flightAirplaneClassDetailRepository.save(airplaneClass);

                //Set seat status
                FlightAirplaneSeat seat = flightAirplaneSeatRepository.findFlightAirplaneSeatByFlight_IdAndAirplaneSeat_SeatCodeAndAirplaneSeat_AirplaneClassOption_Id(ticket.getFlightDetail().getId(), ticket.getSeat().getSeatCode(), ticket.getSeat().getAirplaneClassOption().getId())
                        .orElseThrow(() -> new RuntimeException("Seat not found"));
                seat.setAvailable(true);
                flightAirplaneSeatRepository.save(seat);

            } catch (Exception ignored) {

            }

        });
        logger.info("TASK COMPLETED: Deleted all timeout pending booking in database");
    }

}
