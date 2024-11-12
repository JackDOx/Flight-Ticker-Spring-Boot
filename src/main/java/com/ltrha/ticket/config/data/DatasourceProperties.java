package com.ltrha.ticket.config.data;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DatasourceProperties {
    private String driver;
    private String user;
    private String password;
    private String url;

}
