package com.ltrha.ticket.repositories;

import com.ltrha.ticket.models.FlightAirplaneSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlightAirplaneSeatRepository extends JpaRepository<FlightAirplaneSeat, Integer> {

    public List<FlightAirplaneSeat> findFlightAirplaneSeatByFlight_Id(int flightDetailId);

    public List<FlightAirplaneSeat> findByFlight_FlightCode(String flightCode);

    public Optional<FlightAirplaneSeat> findFlightAirplaneSeatByFlight_IdAndAirplaneSeat_SeatCodeAndAirplaneSeat_AirplaneClassOption_Id(int flightDetailId, String seatCode, int airplaneClassOptionId);
}
