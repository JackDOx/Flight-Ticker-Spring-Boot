package com.ltrha.ticket.event.booking;

import com.ltrha.ticket.domain.mapper.SendBookingMailMapper;
import com.ltrha.ticket.domain.request.mail.SendBookingSuccessfulMailRequest;
import com.ltrha.ticket.repositories.TicketRepository;
import com.ltrha.ticket.service.impl.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OnBookingFlightSuccessEventListener implements ApplicationListener<OnBookingFlightSuccessEvent> {

    private final MailService mailService;
    private final TicketRepository ticketRepository;
    private final SendBookingMailMapper sendBookingMailMapper;

    @Override
    public void onApplicationEvent(@NonNull OnBookingFlightSuccessEvent event) {
        SendBookingSuccessfulMailRequest request = ticketRepository.findByIdFetchAll(UUID.fromString(event.getTicketId()))
                .map((ticket) -> sendBookingMailMapper.toSendBookingSuccessfulMailRequest(ticket, event.getCurrentUser().getEmail()))
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        try {
            mailService.sendBookingSuccessfulMail(request);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
