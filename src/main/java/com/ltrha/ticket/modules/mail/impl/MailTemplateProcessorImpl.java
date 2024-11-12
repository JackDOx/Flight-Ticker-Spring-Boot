package com.ltrha.ticket.modules.mail.impl;

import com.ltrha.ticket.modules.mail.MailTemplateProcessor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MailTemplateProcessorImpl implements MailTemplateProcessor {

    public String processTemplate(String template, Map<String, Object> data) {

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            template = template.replaceAll("\\{\\{" + entry.getKey() + "\\}\\}", entry.getValue().toString());
        }
        return template;
    }
}
