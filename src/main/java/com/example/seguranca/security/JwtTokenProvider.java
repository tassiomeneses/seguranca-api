package com.example.seguranca.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider extends CommonsJwtTokenProvider {

    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return getToken(userPrincipal);
    }

    public String generateToken(UserPrincipal userPrincipal) {
        return getToken(userPrincipal);
    }

    private String getToken(UserPrincipal userPrincipal) {
        long currentTime = System.currentTimeMillis();

        return Jwts.builder()
            .setClaims(getClaims(userPrincipal))
            .setIssuedAt(new Date(currentTime))
            .setExpiration(new Date(currentTime + jwtExpirationInMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    private Claims getClaims(UserPrincipal userPrincipal) {
        Claims claims = Jwts.claims().setSubject(userPrincipal.getId().toString());
        claims.put("name", userPrincipal.getName());
        claims.put("username", userPrincipal.getUsername());
        claims.put("email", userPrincipal.getEmail());
        if(Objects.nonNull(userPrincipal.getAuthorities()))
        claims.put("roles", userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        //claims.put("sector", userPrincipal.getSector());
        claims.put("cpf", userPrincipal.getCpf());
        claims.put("firstAccess", Optional.ofNullable(userPrincipal.getFirstAccess()).orElse(Boolean.FALSE));
        claims.put("app", userPrincipal.getApp());

        return claims;
    }

}
