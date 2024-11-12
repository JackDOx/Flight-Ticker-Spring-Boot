package com.ltrha.ticket.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

public class CookieUtils {

    public static String getCookie(String cookieName, HttpServletRequest request) {
        //Get from cookie
        Cookie[] cookies = request.getCookies();
        if(cookies == null ||  cookies.length == 0){
            return null;
        }
        Cookie cookie =  Arrays.stream(cookies)
                .filter(c -> c.getName().equals(cookieName))
                .findFirst()
                .orElse(null);

        return cookie != null ? cookie.getValue() : null;
    }

    public static String deleteCookie(String cookieName, HttpServletRequest request) {
        //Get from cookie
        Cookie[] cookies = request.getCookies();
        Cookie cookie =  Arrays.stream(cookies)
                .filter(c -> c.getName().equals(cookieName))
                .findFirst()
                .orElse(null);

        if(cookie != null){
            cookie.setMaxAge(0);
        }

        return cookie != null ? cookie.getValue() : null;
    }

    public static void addCookie(String cookieName, String cookieValue, HttpServletRequest request) {
        //Get from cookie
        Cookie[] cookies = request.getCookies();
        Cookie cookie =  Arrays.stream(cookies)
                .filter(c -> c.getName().equals(cookieName))
                .findFirst()
                .orElse(null);

        if(cookie != null){
            cookie.setValue(cookieValue);
        }

    }
}
