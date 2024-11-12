package com.ltrha.ticket.service.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ltrha.ticket.models.TicketEntity;
import com.ltrha.ticket.models.UserEntity;
import com.ltrha.ticket.repositories.TicketRepository;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MailServiceTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private MailService mailService;

    @Test
    void sendBookingSuccessfulMail() {
//        var ticket = new TicketEntity();
//        ticket.setId(new ObjectIdGenerators.UUIDGenerator().generateId(null));
//
//        UserEntity user = new UserEntity();
//        user.setFullName("Le Trung Ha");
//        user.setEmail("halthe180116@fpt.edu.vn");
//        try {
//            mailService.sendBookingSuccessfulMail(ticket, user);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}