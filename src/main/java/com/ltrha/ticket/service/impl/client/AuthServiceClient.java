package com.ltrha.ticket.service.impl.client;

import com.ltrha.ticket.domain.ValidateTokenRequest;
import com.ltrha.ticket.domain.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.ltrha.ticket.constant.service.AuthService.AUTH_SERVICE_URL;

@Service
@RequiredArgsConstructor
public class AuthServiceClient {
    private final RestTemplate restTemplate;

    public boolean validateToken(String token) {
        ValidateTokenRequest validateTokenRequest = new ValidateTokenRequest(token);
        try {
            restTemplate.postForObject( AUTH_SERVICE_URL + "/validate-token", validateTokenRequest, ApiResponse.class);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
