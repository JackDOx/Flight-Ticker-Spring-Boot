package com.ltrha.ticket.modules.mail;

import java.util.Map;

public interface MailTemplateProcessor {

    public String processTemplate(String template, Map<String, Object> props);
}
