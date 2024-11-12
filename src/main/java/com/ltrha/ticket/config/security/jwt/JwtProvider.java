package com.ltrha.ticket.config.security.jwt;


import com.ltrha.ticket.config.security.UserPrincipal;
import com.ltrha.ticket.models.UserEntity;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;

@Component
public class JwtProvider {

    private static final Logger logger = LogManager.getLogger(JwtProvider.class);
    public static final String ISSUER_GENERATE_TOKEN = "FlightTicketSystem";
    public static final String ISSUER_GENERATE_REFRESH_TOKEN = "FlightTicketSystemRefreshToken";
    public static final String SIGNING_KEY = "HelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorld";

    public enum InvalidTokenReason {
        EXPIRED_TOKEN,
        OTHER
    }

    public String generateJwtToken(final Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000);

        return Jwts.builder()
                .subject((userPrincipal.getUsername()))
                .issuer(ISSUER_GENERATE_TOKEN)
                .id(String.valueOf(userPrincipal.getId()))
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    public String generateTokenFromUser(final UserEntity userEntity) {
        Instant expiryDate = Instant.now().plusMillis(3600000);
        return Jwts.builder()
                .subject(userEntity.getEmail())
                .issuer(ISSUER_GENERATE_REFRESH_TOKEN)
                .id(String.valueOf(userEntity.getId()))
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(expiryDate))
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    public String getUserNameFromJwtToken(final String token) {
        var payload = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();


        return payload.getSubject();

    }
    public String getUserIdFromJwtToken(final String token) {
        var payload = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return payload.getId();
    }

    public Date getTokenExpiryFromJWT(final String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getExpiration();
    }

    public static record validateJwtTokenResponse(boolean isValid, InvalidTokenReason reason) {
    }

    public validateJwtTokenResponse validateJwtToken(final String authToken) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(authToken);

            return new validateJwtTokenResponse(true, null);
        } catch (MalformedJwtException e) {
//            logger.error("Invalid JWT token -> Message: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
//            return new validateJwtTokenResponse(false, InvalidTokenReason.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
//            logger.error("Unsupported JWT token -> Message: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
//            logger.error("JWT claims string is empty -> Message: {}", e.getMessage());
        }

        return new validateJwtTokenResponse(false, InvalidTokenReason.OTHER);
    }


    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SIGNING_KEY.getBytes());
    }

    public long getExpiryDuration() {
        return 3600000;
    }
}