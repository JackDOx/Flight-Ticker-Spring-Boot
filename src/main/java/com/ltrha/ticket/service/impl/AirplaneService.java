package com.ltrha.ticket.service.impl;

import com.ltrha.ticket.domain.mapper.AirplaneMapper;
import com.ltrha.ticket.domain.request.airplane.AddAirplaneRequest;
import com.ltrha.ticket.domain.response.airplane.AirplaneDetailResponse;
import com.ltrha.ticket.repositories.AirplaneRepository;
import com.ltrha.ticket.repositories.AirplaneSeatOptionRepository;
import com.ltrha.ticket.repositories.AirplaneSeatRepository;
import com.ltrha.ticket.repositories.FlightAirplaneClassDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirplaneService {

    private final AirplaneRepository airplaneRepository;
    private final AirplaneSeatRepository airplaneSeatRepository;
    private final FlightAirplaneClassDetailRepository flightAirplaneClassDetailRepository;
    private final AirplaneSeatOptionRepository airplaneSeatOptionRepository;
    private final AirplaneMapper airplaneMapper;

    public AirplaneDetailResponse findById(int id) {
        var entity = airplaneRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Airplane cannot be found"));
        return airplaneMapper.toAirplaneDetailResponse(entity);
    }

    public void addAirplane(AddAirplaneRequest request) {
        // Add airplane logic
    }

}
