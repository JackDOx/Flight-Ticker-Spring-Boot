package com.ltrha.ticket.domain.mapper;

import com.ltrha.ticket.domain.response.flight.GetFlightSeatResponse;
import com.ltrha.ticket.models.FlightAirplaneSeat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FlightSeatMapper {

    @Mapping(target = "seatCode", source = "airplaneSeat.seatCode")
    @Mapping(target = "flightClass", source = "airplaneSeat.airplaneClassOption.className")
    GetFlightSeatResponse toFlightSeatResponse(FlightAirplaneSeat flightAirplaneSeat);

}
