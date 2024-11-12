package com.ltrha.ticket.domain.mapper;


import com.ltrha.ticket.domain.response.flight.FlightClassDetailResponse;
import com.ltrha.ticket.models.FlightAirplaneClassDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FlightClassFareMapper {

    @Mapping(target = "flightId", source = "entity.flightDetail.id")
    @Mapping(target = "flightClass", source = "entity.airplaneClassOption.className")
    @Mapping(target = "airplaneFlightClassId", source = "entity.airplaneClassOption.id")
    public FlightClassDetailResponse toFlightClassFareResponse(FlightAirplaneClassDetail entity);



}
