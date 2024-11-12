package com.ltrha.ticket.repositories;

import com.ltrha.ticket.constant.business.TicketBookingStatusCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    void findTicketWithCreatedDateBefore() {
        var result = ticketRepository.findTicketWithCreatedDateBefore(LocalDateTime.now().minusMinutes(15), TicketBookingStatusCode.PENDING);

        assertNotNull(result);
        result.forEach(ticketEntity -> {
            System.out.println(ticketEntity.getCreatedDate());
        });
    }
}