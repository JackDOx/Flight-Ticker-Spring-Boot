package com.ltrha.ticket.domain.request.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SendActivationMailRequest {
    private String email;

    public SendActivationMailRequest() {
    }


}
