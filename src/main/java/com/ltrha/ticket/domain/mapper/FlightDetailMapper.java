package com.ltrha.ticket.domain.mapper;

import com.ltrha.ticket.domain.response.flight.FlightClassDetailResponse;
import com.ltrha.ticket.domain.response.flight.FlightDetailResponse;
import com.ltrha.ticket.models.FlightDetailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlightDetailMapper {
    @Mapping(target = "airlineId", source = "entity.airline.id")
    @Mapping(target = "airlineName", source = "entity.airline.name")
    @Mapping(target = "airlineCode", source = "entity.airline.code")
    @Mapping(target = "airplaneId", source = "entity.airplane.id")
    @Mapping(target = "flightClassDetails", source = "entity.seatOptionFare")
    FlightDetailResponse toFlightDetailResponse(FlightDetailEntity entity);

    @Mapping(target = "airlineId", source = "entity.airline.id")
    @Mapping(target = "airlineName", source = "entity.airline.name")
    @Mapping(target = "airlineCode", source = "entity.airline.code")
    @Mapping(target = "airplaneId", source = "entity.airplane.id")
    @Mapping(target = "flightClassDetails", source = "fares")
    FlightDetailResponse toFlightDetailResponseWithPriceInfo(FlightDetailEntity entity, List<FlightClassDetailResponse> fares);


}
