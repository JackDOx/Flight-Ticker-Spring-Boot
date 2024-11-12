package com.ltrha.ticket.domain.mapper;

import com.ltrha.ticket.domain.request.mail.SendBookingSuccessfulMailRequest;
import com.ltrha.ticket.models.TicketEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SendBookingMailMapper {

    @Mapping(target = "customerName", source = "ticket.owner.name")
    @Mapping(target = "customerEmail", source = "bookedByEmail")
    @Mapping(target = "flightClass", source = "ticket.airplaneClassOption.className")
    @Mapping(target = "ticketId", source = "ticket.id")
    @Mapping(target = "flightCode", source = "ticket.flightDetail.flightCode")
    @Mapping(target = "departureLocation", source = "ticket.flightDetail.departureLocation.locationName")
    @Mapping(target = "arrivalLocation", source = "ticket.flightDetail.destination.locationName")
    @Mapping(target = "departureLocationCode", source = "ticket.flightDetail.departureLocation.locationCode")
    @Mapping(target = "arrivalLocationCode", source = "ticket.flightDetail.destination.locationCode")
    @Mapping(target = "flightDate", source = "ticket.flightDetail.departDate")
     SendBookingSuccessfulMailRequest toSendBookingSuccessfulMailRequest(TicketEntity ticket, String bookedByEmail);
}
