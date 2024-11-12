package com.ltrha.ticket.modules.mail.impl;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GmailSenderTest {

    @Autowired
    private GmailSender gmailSender;
    @Test
    void send() {
    }

    @Test
    void sendEmail() {
//        try {
//            gmailSender.sendEmail("letrungha2004@gmail.com", "halthe180116@fpt.edu.vn");
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (GeneralSecurityException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Test
    void testSend() {
    }
}