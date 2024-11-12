package com.ltrha.ticket.domain.response.ticket;

import com.ltrha.ticket.models.TicketEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookTicketResponse {
    private String ticketId;
    private long totalPrice;

}
