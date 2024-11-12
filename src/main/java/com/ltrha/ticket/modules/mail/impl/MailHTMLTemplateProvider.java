package com.ltrha.ticket.modules.mail.impl;

import com.ltrha.ticket.config.mail.MailProperties;
import com.ltrha.ticket.modules.mail.MailTemplateProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//@ConfigurationProperties(prefix = "spring.mail")
@Setter
@Getter
@RequiredArgsConstructor
public class MailHTMLTemplateProvider implements MailTemplateProvider {

    private final MailProperties mailProperties;

    Logger logger = LoggerFactory.getLogger(MailHTMLTemplateProvider.class);

    public String getTemplate(String templateName) {

        MailTemplate template = mailProperties.getTemplates().stream()
                .filter(t -> t.getTemplateName().equals(templateName))
                .findFirst()
                .orElse(null);
        if (template == null) {
            logger.warn("Template not found: " + templateName);
            return null;
        }
        return readTemplateFromFile(template.getTemplatePath());


    }

    private String readTemplateFromFile(String filePath) {
        logger.info("Reading template from file: " + filePath);
        ClassPathResource resource = new ClassPathResource(filePath);
        try {
            byte[] data = resource.getInputStream().readAllBytes();
            return new String(data);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

}
