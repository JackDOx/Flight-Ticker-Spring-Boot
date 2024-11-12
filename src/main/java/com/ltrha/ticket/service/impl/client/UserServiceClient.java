package com.ltrha.ticket.service.impl.client;

import com.ltrha.ticket.config.security.UserPrincipal;
import com.ltrha.ticket.models.UserEntity;
import com.ltrha.ticket.utils.auth.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceClient {

    Logger logger = org.slf4j.LoggerFactory.getLogger(UserServiceClient.class);
    private final RestTemplate restTemplate;
    private static final String USER_SERVICE_URL = "http://localhost:8081/api/user";

    public Optional<UserEntity> getUser(String id) {
        return Optional.ofNullable(
                restTemplate.getForObject(USER_SERVICE_URL + "/" + id, UserEntity.class)
        );
    }

    public Optional<UserEntity> getUser(String id, String token) {
        try {
            RestTemplate template = new RestTemplate();
//            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<UserEntity> response = template.exchange(
                    USER_SERVICE_URL + "/" + id,
                    HttpMethod.GET,
                    entity,
                    UserEntity.class
            );

            return Optional.ofNullable(response.getBody());
        } catch (RestClientException e) {
            // Log the exception (or handle it accordingly)
            logger.warn("Error fetching user: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<UserEntity> getUserByEmail(String email) {
        return Optional.ofNullable(
                restTemplate.getForObject(USER_SERVICE_URL + "/user/email", UserEntity.class)
        );
    }
}
