package com.ltrha.ticket.domain.mapper;


import com.ltrha.ticket.constant.business.TicketBookingStatusCode;
import com.ltrha.ticket.domain.response.ticket.TicketResponse;
import com.ltrha.ticket.models.TicketEntity;
import com.ltrha.ticket.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface TicketMapper  {

    TicketEntity toTicketEntity(TicketResponse ticketResponse);

    @Mapping(target="flightCode", source="ticketEntity.flightDetail.flightCode")
    @Mapping(target="seatCode", source="ticketEntity.seat.seatCode")
    @Mapping(target="ownerName", source="ticketEntity.owner.name")
    @Mapping(target="ownerDateOfBirth", source="ticketEntity.owner.dob")
    @Mapping(target="ownerIdentityCard", source="ticketEntity.owner.identityCard")

    @Mapping(target = "flightClass", source = "ticketEntity.airplaneClassOption.className")
    @Mapping(target = "departureLocation", source = "ticketEntity.flightDetail.departureLocation.locationName")
    @Mapping(target = "arrivalLocation", source = "ticketEntity.flightDetail.destination.locationName")
    @Mapping(target = "departureLocationCode", source = "ticketEntity.flightDetail.departureLocation.locationCode")
    @Mapping(target = "arrivalLocationCode", source = "ticketEntity.flightDetail.destination.locationCode")
    @Mapping(target = "flightDate", source = "ticketEntity.flightDetail.departDate")

    @Mapping(target="bookedById", source="ticketEntity.bookedById")
    @Mapping(target="bookedByName", source="bookedBy.fullName")
    @Mapping(target="bookedDate", source="ticketEntity.createdDate")
    @Mapping(target="status", source = "ticketEntity.status", qualifiedByName = "statusConverter")
    @Mapping(target = "statusCode", source = "ticketEntity.status")
    @Mapping(target = "airlineName", source = "ticketEntity.flightDetail.airline.name")
    @Mapping(target = "id", source = "ticketEntity.id")
    TicketResponse toTicketResponse(TicketEntity ticketEntity, UserEntity bookedBy);

    @Named("statusConverter")
    default String convertStatusToString(int status) {
        return TicketBookingStatusCode.getStatus(status);
    }


}
