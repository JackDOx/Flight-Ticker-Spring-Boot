package com.ltrha.ticket.config.data;

import com.ltrha.ticket.config.security.UserPrincipal;
import com.ltrha.ticket.utils.auth.AuthenticationUtils;
import io.micrometer.common.lang.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class DataAudit implements AuditorAware<String> {

    @NonNull
    @Override
    public Optional<String> getCurrentAuditor() {
        UserPrincipal currentUser = AuthenticationUtils.getCurrentUser().orElseThrow(
                () -> new RuntimeException("User not found for audit")
        );
        return Optional.of(currentUser.getId().toString());
    }
}
