package com.ltrha.ticket.modules.builders;


import jakarta.servlet.http.Cookie;

public class CookieBuilder {

    public Cookie cookie;

    public Cookie build(){
        return cookie;
    }

    public CookieBuilder(String name, String value){
        cookie = new Cookie(name, value);
    }

    public CookieBuilder setSameSite(String sameSite){
        cookie.setAttribute("SameSite", sameSite);
        return this;
    }
    public CookieBuilder setSecure(boolean secure){
        cookie.setSecure(secure);
        return this;
    }
    public CookieBuilder setHttpOnly(boolean httpOnly){
        cookie.setHttpOnly(httpOnly);
        return this;
    }
    public CookieBuilder setAttribute(String name, String value){
        cookie.setAttribute(name, value);
        return this;
    }
    public CookieBuilder setMaxAge(int maxAge){
        cookie.setMaxAge(maxAge);
        return this;
    }

}
