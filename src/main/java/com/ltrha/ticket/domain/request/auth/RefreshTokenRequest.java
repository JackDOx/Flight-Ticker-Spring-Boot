package com.ltrha.ticket.domain.request.auth;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RefreshTokenRequest {

    private String refreshToken;
    private String deviceId;
}
