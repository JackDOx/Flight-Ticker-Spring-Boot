package com.ltrha.ticket.domain.request.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LogoutRequest {
    private String refreshToken;


}
