package com.ltrha.ticket.domain.request.auth;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotNull(message = "Email is required")
    @Valid
    private String email;

    @NotNull
    private String password;

    private RequestDeviceInfo requestDeviceInfo;
}
