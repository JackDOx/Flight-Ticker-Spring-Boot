package com.ltrha.ticket.config.mail;

import com.ltrha.ticket.modules.mail.impl.MailTemplate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {
    private String defaultEncoding;
    private String host;
    private Integer port;
    private String username;
    private String password;
    private boolean testConnection;
    private String protocol;
    private Properties properties;

    private List<MailTemplate> templates;

}
