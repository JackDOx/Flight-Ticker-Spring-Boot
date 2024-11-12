package com.ltrha.ticket.modules.pdf;

import java.util.Map;

public class PDFTemplateProcessor {
    public String processTemplate(String template, Map<String, Object> data) {

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            template = template.replaceAll("\\{\\{" + entry.getKey() + "\\}\\}", entry.getValue().toString());
        }
        return template;
    }
}
