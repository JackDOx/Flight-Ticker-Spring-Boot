package com.ltrha.ticket.repositories;

import com.ltrha.ticket.models.FlightDetailEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNullApi;

public interface FlightDetailRepository extends JpaRepository<FlightDetailEntity, Integer> {


    public Page<FlightDetailEntity> findByDepartureLocation_IdAndDestination_Id(int departureLocationId, int destinationId, Pageable pageable);
    public boolean existsByFlightCode(String flightCode);
}
