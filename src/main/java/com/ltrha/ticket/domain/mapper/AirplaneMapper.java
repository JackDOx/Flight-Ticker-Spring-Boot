package com.ltrha.ticket.domain.mapper;

import com.ltrha.ticket.domain.response.airplane.AirplaneDetailResponse;
import com.ltrha.ticket.domain.response.airplane.AirplaneSeatOptionResponse;
import com.ltrha.ticket.models.AirplaneClassOption;
import com.ltrha.ticket.models.AirplaneDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AirplaneMapper {


    public AirplaneDetailResponse toAirplaneDetailResponse(AirplaneDetail entity);


    List<AirplaneSeatOptionResponse> toAirplaneSeatOptionResponseList(List<AirplaneClassOption> seatOptions);

    @Mapping(target = "id", source = "seatOption.id")
    @Mapping(target = "optionName", source = "seatOption.className")
    AirplaneSeatOptionResponse toAirplaneSeatOptionResponse(AirplaneClassOption seatOption);
}
