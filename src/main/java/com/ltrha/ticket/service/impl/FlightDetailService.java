package com.ltrha.ticket.service.impl;

import com.ltrha.ticket.domain.mapper.FlightClassFareMapper;
import com.ltrha.ticket.domain.mapper.FlightDetailMapper;
import com.ltrha.ticket.domain.request.flight.*;
import com.ltrha.ticket.domain.response.flight.FlightDetailResponse;
import com.ltrha.ticket.exception.FlightTicketRuntimeException;
import com.ltrha.ticket.models.FlightAirplaneClassDetail;
import com.ltrha.ticket.models.FlightAirplaneSeat;
import com.ltrha.ticket.models.FlightDetailEntity;
import com.ltrha.ticket.pagination.PaginationPage;
import com.ltrha.ticket.pagination.PaginationRequest;
import com.ltrha.ticket.repositories.*;
import com.ltrha.ticket.utils.date.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightDetailService {

    private final FlightDetailRepository flightDetailRepository;
    private final AirportRepository airportRepository;
    private final AirlineRepository airlineRepository;
    private final AirplaneRepository airplaneRepository;
    private final AirplaneSeatOptionRepository airplaneSeatOptionRepository;
    private final FlightAirplaneClassDetailRepository flightAirplaneClassDetailRepository;
    private final AirplaneSeatRepository airplaneSeatRepository;
    private final FlightAirplaneSeatRepository flightAirplaneSeatRepository;
    private final LocationRepository locationRepository;

    //Mapper
    private final FlightDetailMapper flightDetailMapper;
    private final FlightClassFareMapper flightClassFareMapper;

    private RuntimeException handleSystemError(String message, Throwable e) {
        log.warn(e.getMessage(), e);
        return new FlightTicketRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public FlightDetailResponse findById(int id) {
        var entity = flightDetailRepository
                .findById(id)
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Flight cannot be found"));
        var flightClassFareResposne = entity.getSeatOptionFare()
                .stream()
                .map(flightClassFareMapper::toFlightClassFareResponse)
                .toList();
        return flightDetailMapper.toFlightDetailResponseWithPriceInfo(entity, flightClassFareResposne);
    }

    public void addFlight(AddFlightRequest request) {

        if (request.getDestinationId().equals(request.getDepartureLocationId())) {
            throw new FlightTicketRuntimeException(HttpStatus.BAD_REQUEST, "Departure location and destination cannot be the same");
        }
        if (request.getDepartureAirportId().equals(request.getDestinationAirportId())) {
            throw new FlightTicketRuntimeException(HttpStatus.BAD_REQUEST, "Departure airport and destination airport cannot be the same");
        }

        if (flightDetailRepository.existsByFlightCode(request.getFlightCode())) {
            throw new FlightTicketRuntimeException(HttpStatus.BAD_REQUEST, "Flight code already exists");
        }


        var airline = airlineRepository.findById(request.getAirlineId())
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Airline cannot be found"));

        var destination = locationRepository.findById(request.getDestinationId())
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Destination cannot be found"));

        var departureLocation = locationRepository.findById(request.getDepartureLocationId())
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Departure location cannot be found"));

        var departureAirport = airportRepository.findById(request.getDepartureAirportId())
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Departure airport cannot be found"));

        var destinationAirport = airportRepository.findById(request.getDestinationAirportId())
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Destination airport cannot be found"));


        var airplane = airplaneRepository.findById(request.getAirplaneId())
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Airplane cannot be found"));
        //Create flight entity

        var flight = new FlightDetailEntity(
                0,
                request.getFlightCode(),
                airline,
                null,
                null,
                airplane,
                DateUtils.parseLocalDateTimeFromString(request.getDepartDate(), DateUtils.CommonDateFormat.DD_MM_YYYY_HH_MM),
                DateUtils.parseLocalDateTimeFromString(request.getEstimatedArrivedDate(), DateUtils.CommonDateFormat.DD_MM_YYYY_HH_MM),
                departureAirport,
                destinationAirport,
                departureLocation,
                destination
        );

        try {

            flightDetailRepository.saveAndFlush(flight);

        } catch (Exception e) {
            throw handleSystemError("System error! Cannot add flight", e);
        }

        //Add price and avaible seat for flight class
        for (UpdateSeatPriceForFlightClassRequest updateSeatPriceForFlightClassRequestRequest : request.getSeatFaresForFlightClass()) {
            var airplaneSeatOption = airplaneSeatOptionRepository.findById(updateSeatPriceForFlightClassRequestRequest.getAirplaneSeatOptionId())
                    .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Airplane seat option cannot be found"));

            FlightAirplaneClassDetail fare = new FlightAirplaneClassDetail(
                    0,
                    flight,
                    airplaneSeatOption,
                    updateSeatPriceForFlightClassRequestRequest.getPrice(),
                    airplaneSeatOption.getSeatCount(),
                    airplaneSeatOption.getSeatCount()
            );
            try {
                flightAirplaneClassDetailRepository.save(fare);
            } catch (Exception e) {
                throw handleSystemError("System error! Cannot add flight seat", e);
            }
        }

        //AddFlightSeat
        var airplaneSeats = airplaneSeatRepository.findByAirplaneDetail_Id(airplane.getId());
        var flightAirplaneSeats = new ArrayList<FlightAirplaneSeat>();
        for (var seat : airplaneSeats) {
            var flightAirplaneSeat = new FlightAirplaneSeat(
                    0,
                    seat,
                    flight,
                    true
            );
            flightAirplaneSeats.add(flightAirplaneSeat);
        }
        try {
            flightAirplaneSeatRepository.saveAllAndFlush(flightAirplaneSeats);
        } catch (Exception e) {
            throw handleSystemError("System error! Cannot add flight seat", e);
        }


    }


    @Transactional
    public void updateFlightDetail(UpdateFlightRequest request) {

        var flightDetailEntity = flightDetailRepository.findById(request.getFlightId())
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Flight cannot be found"));

        // Fetch both locations in a single call
        var locations = locationRepository.findAllById(Arrays.asList(request.getDepartureLocationId(), request.getDestinationId()));
        if (locations.size() != 2) {
            throw new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "One or both locations cannot be found");
        }

        var departureLocation = locations.stream()
                .filter(loc -> loc.getId() == (request.getDepartureLocationId()))
                .findFirst()
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Departure location cannot be found"));

        var destination = locations.stream()
                .filter(loc -> loc.getId() == (request.getDestinationId()))
                .findFirst()
                .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Destination cannot be found"));

        // Collect seat fare updates

        if (request.getUpdateSeatFaresForFlightClassRequest() != null) {
            var flightClassFares = new ArrayList<FlightAirplaneClassDetail>();

            request.getUpdateSeatFaresForFlightClassRequest().forEach(updateClassFareRequest -> {
                var flightClassFare = flightAirplaneClassDetailRepository
                        .findByAirplaneClassOption_IdAndFlightDetail_Id(updateClassFareRequest.getAirplaneSeatOptionId(), updateClassFareRequest.getFlightId())
                        .orElseThrow(() -> new FlightTicketRuntimeException(HttpStatus.NOT_FOUND, "Flight seat cannot be found"));

                flightClassFare.setFare(updateClassFareRequest.getPrice());
                flightClassFares.add(flightClassFare);
            });

            // Save all seat fares in a batch
            flightAirplaneClassDetailRepository.saveAll(flightClassFares);
        }

        // Update flight details
        flightDetailEntity.setDepartDate(request.getDepartDate());
        flightDetailEntity.setEstimatedArrivedDate(request.getEstimatedArrivedDate());
        flightDetailEntity.setFlightCode(request.getFlightCode());
        flightDetailEntity.setDepartureLocation(departureLocation);
        flightDetailEntity.setDestination(destination);

        // Save flight details
        flightDetailRepository.save(flightDetailEntity);
    }


//    @Cacheable(value = "flights", key = "#root.methodName")
    public PaginationPage<FlightDetailResponse> findByDepartureLocationIdAndDestination(int departureLocationId, int destinationId, int page, int limit) {
        try {
            var paginationRequest = new PaginationRequest(page, limit);
            var flightsPage = flightDetailRepository
                    .findByDepartureLocation_IdAndDestination_Id(departureLocationId, destinationId, paginationRequest)
                    .map(flightDetailMapper::toFlightDetailResponse);
            return PaginationPage.fromPage(flightsPage);
        } catch (Exception e) {
            throw handleSystemError("System error! Cannot get flight", e);
        }
    }

//    @Cacheable(value = "flights", key="#root.methodName")
    public List<FlightDetailResponse> getMany() {
        try {
            return flightDetailRepository.findAll()
                    .stream()
                    .map(flightDetailMapper::toFlightDetailResponse).toList();
        } catch (Exception e) {
            throw handleSystemError("System error! Cannot get flight", e);

        }
    }


    public void removeFlight(int flightId) {
        try {

        } catch (Exception e) {
            throw handleSystemError("System error! Cannot remove flight", e);
        }
    }

    public List<FlightDetailEntity> getAllFlights() {
        try {
            return flightDetailRepository.findAll();

        } catch (Exception e) {
            throw handleSystemError("System error! Cannot get all flights", e);
        }
    }
}
