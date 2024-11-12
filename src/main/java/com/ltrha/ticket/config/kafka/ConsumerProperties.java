package com.ltrha.ticket.config.kafka;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "spring.kafka.consumer")
@Getter
@Setter
public class ConsumerProperties {
    private String bootstrapServers;
    private String groupId;
}
