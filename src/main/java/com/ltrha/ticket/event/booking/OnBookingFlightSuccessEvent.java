package com.ltrha.ticket.event.booking;

import com.ltrha.ticket.domain.response.ticket.TicketResponse;
import com.ltrha.ticket.models.TicketEntity;
import com.ltrha.ticket.models.UserEntity;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

@Getter
public class OnBookingFlightSuccessEvent extends ApplicationEvent {
    private final String ticketId;
    private final UserEntity currentUser;
    public OnBookingFlightSuccessEvent(@NonNull String ticketId,@NonNull UserEntity currentUser) {
        super(ticketId);
        this.ticketId = ticketId;
        this.currentUser = currentUser;
    }
}
