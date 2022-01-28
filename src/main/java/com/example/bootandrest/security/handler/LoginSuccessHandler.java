package com.example.bootandrest.security.handler;

import com.example.bootandrest.service.CustomOAuth2User;
import com.example.bootandrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginSuccessHandler implements AuthenticationSuccessHandler {



    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
       // Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
    //  httpServletResponse.sendRedirect("/");
       // System.out.println("AuthenticationSuccessHandler invoked");
        System.out.println("Authentication name: " + authentication.getName());
        System.out.println("Authentication name: " + authentication.getPrincipal());
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
   //     userService.processOAuthPostLogin(oAuth2User.getEmail());
        httpServletResponse.sendRedirect("/");
    }
}