package com.ltrha.ticket.service.impl;

import com.ltrha.ticket.constant.enums.CommonEnums;
import com.ltrha.ticket.domain.request.mail.SendBookingSuccessfulMailRequest;
import com.ltrha.ticket.models.FlightDetailEntity;
import com.ltrha.ticket.models.TicketEntity;
import com.ltrha.ticket.models.UserEntity;
import com.ltrha.ticket.modules.mail.MailTemplateProcessor;
import com.ltrha.ticket.modules.mail.MailTemplateProvider;
import com.ltrha.ticket.utils.date.DateUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Service
public class MailService {

    Logger logger = LoggerFactory.getLogger(MailService.class);

    private static final String MAIL_FROM = "letrungha2004@gmail.com";
    private static final String MAIL_PERSONAL = "QuickFlight";

    private final JavaMailSender mailSender;
    private final MailTemplateProcessor mailTemplateProcessor;
    private final MailTemplateProvider mailTemplateProvider;

    @Autowired
    public MailService(@Qualifier("gmailSender") JavaMailSender mailSender, MailTemplateProcessor mailTemplateProcessor, MailTemplateProvider mailTemplateProvider) {
        this.mailSender = mailSender;
        this.mailTemplateProcessor = mailTemplateProcessor;
        this.mailTemplateProvider = mailTemplateProvider;
    }

    public void sendAccountActivationMail() {


    }

    public void sendBookingSuccessfulMail(SendBookingSuccessfulMailRequest request) throws MessagingException, IOException {
        Map<String, Object> props = Map.ofEntries(
                Map.entry("userName", request.getCustomerName()),
                Map.entry("flightClass", request.getFlightClass()),
                Map.entry("ticketId", request.getTicketId()),
                Map.entry("flightCode", request.getFlightCode()),
                Map.entry("departure", request.getDepartureLocationCode() + " - " + request.getDepartureLocation()),
                Map.entry("arrival", request.getArrivalLocationCode() + " - " + request.getArrivalLocation()),
                Map.entry("flightDate", request.getFlightDate())
//
        );

        sendMail(request.getCustomerEmail(), "Booking successful", CommonEnums.MailTemplate.BOOKING_SUCCESSFUL, props, null);

    }

    public void sendActivationEmail(UserEntity user, String activationLink) throws MessagingException, IOException {

        Map<String, Object> props = Map.ofEntries(
                Map.entry("userName", Optional.ofNullable(user.getFullName()).orElseThrow()),
                Map.entry("activationLink", activationLink)
        );
        sendMail(user.getEmail(), "Activate your account", CommonEnums.MailTemplate.ACCOUNT_ACTIVATION, props, null);

    }

    public void sendResetPasswordEmail(UserEntity user, String token, int expireAfter) {
        Map<String, Object> props = Map.ofEntries(
                Map.entry("fullName", Optional.ofNullable(user.getFullName()).orElseThrow()),
                Map.entry("resetPasswordLink", token),
                Map.entry("expiryAfter", expireAfter)
        );

        sendMail(user.getEmail(), "Reset password", CommonEnums.MailTemplate.RESET_PASSWORD, props, null);
    }


    private void sendMail(String to, String subject, CommonEnums.MailTemplate template, Map<String, Object> props,
                          Map<String, InputStreamSource> attachments) {

        String content = generateMailContent(template, props);
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setSubject(Optional.ofNullable(subject).orElseThrow());
            mimeMessageHelper.setFrom(MAIL_FROM, MAIL_PERSONAL);
            mimeMessageHelper.setTo(Optional.ofNullable(to).orElseThrow());
            mimeMessageHelper.setText(Optional.ofNullable(content).orElseThrow(), true);

            if (attachments != null) {

                attachments.forEach((name, source) -> {
                    try {
                        mimeMessageHelper.addAttachment(name, source);
                    } catch (MessagingException e) {
                        logger.warn("Error adding attachment to email", e);
                        throw new RuntimeException(e);
                    }
                });
            }
            //Send email
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            logErrorSendingEmail(e, new String[]{to});
        }
    }

    private String generateMailContent(CommonEnums.MailTemplate template, Map<String, Object> props) {
        //Get html content from template
        String htmlTemplate = mailTemplateProvider.getTemplate(template.getTemplateName());
        //Process template

        return mailTemplateProcessor.processTemplate(htmlTemplate, props);

    }


    private void logErrorSendingEmail(final Exception e, String[] to) {
        logger.error("Error sending email to '{}'", Optional.ofNullable(to)
                .map(Arrays::toString)
                .orElseGet(() -> "Unknown"), e);
    }


}
