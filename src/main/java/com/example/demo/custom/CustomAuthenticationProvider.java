package com.example.demo.custom;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials().toString();
        System.out.printf("CustomAuthenticationProvider user: %s with password: %s \n" ,username, password);
        if ("abc".equalsIgnoreCase(username)) {
            List<SimpleGrantedAuthority> rights = Arrays.asList(
                    new SimpleGrantedAuthority("ROLE_USER"),
                    new SimpleGrantedAuthority("ROLE_ADMIN")
            );
            return new CustomAuthenticationToken(username,  rights );
            //GrantedAuthority grant = new SimpleGrantedAuthority("USER");
            /**
            return new UsernamePasswordAuthenticationToken
                    (username, "111",
                            Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"),
                                    new SimpleGrantedAuthority("ROLE_ADMIN")));
             **/
        } else {
            System.out.println("CustomAuthenticationProvider Failed");
            throw new
                    BadCredentialsException("External system authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        //
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
