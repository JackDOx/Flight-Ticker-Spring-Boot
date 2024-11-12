package com.ltrha.ticket.modules.mail.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailHTMLTemplateProviderTest {

    @Autowired
    private MailHTMLTemplateProvider mailHTMLTemplateProvider;

    @Test
    void getTemplate() {
        String template = mailHTMLTemplateProvider.getTemplate("account-activation");
        System.out.println(template);
        assertNotNull(template);
    }
}