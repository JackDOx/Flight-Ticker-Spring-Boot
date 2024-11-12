package com.ltrha.ticket.domain.request.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String email;
    private String fullName;
    private String phoneNumber;
    private String password;
    private String confirmPassword;
}
