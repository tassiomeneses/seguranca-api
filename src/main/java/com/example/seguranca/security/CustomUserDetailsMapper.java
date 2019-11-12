package com.example.seguranca.security;

import com.example.seguranca.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;

@Component
public class CustomUserDetailsMapper {

    @Autowired
    private CustomUserDetailsService service;

    public UserDetails mapUserFromContext(String username, Collection< ? extends GrantedAuthority> authorities) {
        UserDetails userDetails = service.loadUserByUsername(username);

        if(Objects.isNull(userDetails)) {
            throw new UnauthorizedException("error.ldapUser.invalid");
        }

        return userDetails;
    }

}
