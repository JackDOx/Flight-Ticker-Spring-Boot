package com.ltrha.ticket.repositories;

import com.ltrha.ticket.models.FlightAirplaneClassDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlightAirplaneClassDetailRepository extends JpaRepository<FlightAirplaneClassDetail, Integer> {

    public Optional<FlightAirplaneClassDetail> findByAirplaneClassOption_IdAndFlightDetail_Id(int airplaneClassOptionId, int flightDetailId);

}
