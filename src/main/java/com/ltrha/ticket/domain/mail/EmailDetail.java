package com.ltrha.ticket.domain.mail;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetail {
    private String template;
    private String subject;
    private String from;
    private String baseUrl;
}
