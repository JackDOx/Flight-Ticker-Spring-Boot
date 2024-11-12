package com.ltrha.ticket.domain.response.ticket;


import com.ltrha.ticket.models.BaseEntity.BaseEntity;
import com.ltrha.ticket.models.FlightDetailEntity;
import com.ltrha.ticket.models.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class TicketResponse {

    private UUID id;
    private String ownerName;
    private String ownerDateOfBirth;
    private String ownerIdentityCard;
    private String airlineName;
    private long fare;

    //Seat
    private String flightCode;
    private String seatCode;
    private String flightClass;

    private String airplaneClass;

    //Location
    private String departureLocation;
    private String arrivalLocation;
    private String departureLocationCode;
    private String arrivalLocationCode;
    private String flightDate;


    //Booked by
    private UUID bookedById;
    private String bookedByName;
    private String bookedDate;

    private String status;
    private int statusCode;


}
