package com.ltrha.ticket.domain.request.ticket;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CheckinRequest {
    private UUID ticketId;
    private String passportNumber;
    private String seatCode;



}
