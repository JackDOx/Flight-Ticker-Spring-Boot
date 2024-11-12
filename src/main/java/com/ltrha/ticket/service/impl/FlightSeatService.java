package com.ltrha.ticket.service.impl;


import com.ltrha.ticket.domain.mapper.FlightSeatMapper;

import com.ltrha.ticket.domain.response.flight.GetFlightSeatResponse;
import com.ltrha.ticket.exception.FlightTicketRuntimeException;
import com.ltrha.ticket.models.FlightAirplaneSeat;

import com.ltrha.ticket.repositories.FlightAirplaneSeatRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightSeatService {

    private final FlightSeatMapper flightSeatMapper;
    private final FlightAirplaneSeatRepository flightAirplaneSeatRepository;

    public List<GetFlightSeatResponse> getFlightSeatsInfoByFlightCode(String flightCode) {
        try {
            return flightAirplaneSeatRepository.findByFlight_FlightCode(flightCode)
                    .stream()
                    .map(flightSeatMapper::toFlightSeatResponse)
                    .toList();
        } catch (Exception e) {
            throw new FlightTicketRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot get flight seats info");
        }
    }

}
