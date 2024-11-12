package com.ltrha.ticket.config.security.password;

import com.ltrha.ticket.modules.password.PasswordHasher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityPasswordConfig {
    @Bean
    public PasswordEncoder getPasswordEncoder(){

        return new BCryptPasswordEncoder(4);
    }
}
