package com.ltrha.ticket.modules.mail.impl;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.gmail.Gmail;
import com.ltrha.ticket.config.google.GoogleServiceProvider;
import io.micrometer.common.lang.NonNull;
import jakarta.mail.MessagingException;
import com.google.api.services.gmail.model.Message;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class GmailSender implements JavaMailSender {


    private final Gmail gmailService;

    public void sendEmail(MimeMessage mimeMessage)
            throws MessagingException, IOException, GeneralSecurityException {
        /* Load pre-authorized user credentials from the environment.*/



        // Encode and wrap the MIME message into a gmail message
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        mimeMessage.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);

        //Create message
        Message message = new Message();
        message.setRaw(encodedEmail);

        try {
            // Create send message
            message = gmailService.users().messages().send("me", message).execute();
            System.out.println("Message id: " + message.getId());
            System.out.println(message.toPrettyString());
        } catch (GoogleJsonResponseException e) {
            // TODO - handle error appropriately
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 403) {
                System.err.println("Unable to send message: " + e.getDetails());
            } else {
                throw e;
            }
        }
    }


    @Override
    @NonNull
    public MimeMessage createMimeMessage() {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        return new MimeMessage(session);
    }

    @Override
    @NonNull
    public MimeMessage createMimeMessage(@NonNull InputStream contentStream) throws MailException {
        Properties props = new Properties();
        try {
            props.load(contentStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Session session = Session.getDefaultInstance(props, null);

        return new MimeMessage(session);
    }

    @Override
    public void send(@NonNull MimeMessage mimeMessages) throws MailException {
        try {
            sendEmail(mimeMessages);
        } catch (MessagingException | IOException | GeneralSecurityException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void send(MimeMessage... mimeMessages) throws MailException {
        for (MimeMessage mimeMessage : mimeMessages) {
            try {
                sendEmail(mimeMessage);
            } catch (MessagingException | IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void send(@NonNull MimeMessagePreparator mimeMessagePreparator) throws MailException {
        JavaMailSender.super.send(mimeMessagePreparator);
    }

    @Override
    public void send(@NonNull MimeMessagePreparator... mimeMessagePreparators) throws MailException {
        JavaMailSender.super.send(mimeMessagePreparators);
    }

    @Override
    public void send(@NonNull SimpleMailMessage simpleMessage) throws MailException {
        JavaMailSender.super.send(simpleMessage);
    }

    @Override
    public void send(@NonNull SimpleMailMessage... simpleMessages) throws MailException {

    }
}
