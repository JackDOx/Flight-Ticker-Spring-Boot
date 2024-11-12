package com.ltrha.ticket.config.client;

import com.ltrha.ticket.config.security.UserPrincipal;
import com.ltrha.ticket.utils.auth.AuthenticationUtils;
import io.micrometer.common.lang.NonNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthInterceptor implements ClientHttpRequestInterceptor {
    @Override
    @NonNull
    public ClientHttpResponse intercept(@NonNull HttpRequest request,@NonNull byte[] body, ClientHttpRequestExecution execution) throws IOException {
        AuthenticationUtils.getCurrentUser().ifPresent((user) -> {
            request.getHeaders().add("Authorization", "Bearer " + user.getToken());
        });
        return execution.execute(request, body);
    }
}
