package com.ltrha.ticket.config.security.filter;


import com.ltrha.ticket.config.security.UserPrincipal;
import com.ltrha.ticket.config.security.jwt.JwtProvider;
import com.ltrha.ticket.service.impl.client.AuthServiceClient;
import com.ltrha.ticket.service.impl.client.UserServiceClient;
import com.ltrha.ticket.utils.CookieUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    private final AuthServiceClient authServiceClient;
    private final UserServiceClient userServiceClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            String token = getJwt(request);

            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

//            var validateJwtResponse = jwtProvider.validateJwtToken(token);
//
//            //Refresh token if access token is expired
//            if (validateJwtResponse.reason() == JwtProvider.InvalidTokenReason.EXPIRED_TOKEN) {
//                JwtResponse jwtResponse = refreshToken(token);
//                if (jwtResponse == null) {
//                    //Logout if refresh token is expired
//                    CookieUtils.deleteCookie("token", request);
//                    return;
//                }
//                token = jwtResponse.getAccessToken();
//                CookieUtils.addCookie(CookieConstant.TOKEN, token, request);
//            }


            boolean isTokenValid = authServiceClient.validateToken(token);

            if (isTokenValid) {
                //Authenticate
                UserPrincipal userPrincipal = userServiceClient.getUser(jwtProvider.getUserIdFromJwtToken(token), token)
                        .map((user) -> UserPrincipal.build(user, token))
                        .orElseThrow(() -> new RuntimeException("User not found with id: " + jwtProvider.getUserNameFromJwtToken(token)));

                userPrincipal.setToken(token);
                //Create authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //Set context
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);

            }


        } catch (Exception e) {
            logger.error("Cannot set user authentication", e);
        }

        filterChain.doFilter(request, response);
    }


    private String getJwt(final HttpServletRequest request) {
        //Get from header
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }

        //Get from cookie
        return CookieUtils.getCookie("token", request);
    }
}
