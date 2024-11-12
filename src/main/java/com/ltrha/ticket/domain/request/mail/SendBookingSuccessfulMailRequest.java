package com.ltrha.ticket.domain.request.mail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SendBookingSuccessfulMailRequest {
    private String customerName;
    private String customerEmail;
    private String flightClass;
    private String ticketId;
    private String flightCode;
    private String departureLocation;
    private String arrivalLocation;
    private String departureLocationCode;
    private String arrivalLocationCode;
    private String flightDate;
}
