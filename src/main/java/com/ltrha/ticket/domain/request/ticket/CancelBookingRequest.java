package com.ltrha.ticket.domain.request.ticket;

import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CancelBookingRequest {
    private UUID ticketId;

}
