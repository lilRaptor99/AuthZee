package com.pratheeks.AuthZee.auth;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class AuthService {

    public static Cookie getUserTokenCookie(HttpServletRequest request){
        Cookie[] requestCookies = request.getCookies();
        Cookie requestTokenCookie = null;
        if(requestCookies != null) {
            for (Cookie requestCookie : requestCookies) {
                if (requestCookie.getName().equals("userToken")) {
                    requestTokenCookie = requestCookie;
                }
            }
        }
        return requestTokenCookie;
    }
}
