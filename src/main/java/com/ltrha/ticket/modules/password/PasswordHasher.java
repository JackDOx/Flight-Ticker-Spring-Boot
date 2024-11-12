package com.ltrha.ticket.modules.password;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.beans.Encoder;

@Component
@RequiredArgsConstructor
public class PasswordHasher {


    private final PasswordEncoder encoder;

    public String hashPassword(String password) {
        String result = encoder.encode(password);
        return result;
    }

    public boolean verifyPassword(String inputPassword, String hashedPassword) {
        boolean isPasswordCorrect = encoder.matches(inputPassword, hashedPassword);
        return isPasswordCorrect;
    }

}
