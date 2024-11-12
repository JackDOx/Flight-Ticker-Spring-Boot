package com.ltrha.ticket.modules.mail.impl;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
public class MailTemplate {
    private String templateName;
    private String subject;
    private String templatePath;
}
