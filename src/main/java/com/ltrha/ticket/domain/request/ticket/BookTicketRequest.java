package com.ltrha.ticket.domain.request.ticket;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class BookTicketRequest {
    @NotNull(message = "Flight ID is required")
    private int flightId;
    @NotNull(message = "Airplane Class Option ID is required")
    private int airplaneClassOptionId;
    @NotNull(message = "Seat ID is required")
    private String seatCode;
    private String name;
    private String dob;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    @NotNull(message = "Country is required")
    private String country;
    @NotNull(message = "Identity Card is required")
    private String identityCard;

}
