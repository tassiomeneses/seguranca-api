package com.example.seguranca.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public class CommonsJwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(CommonsJwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    protected String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    protected long jwtExpirationInMs;

    public UserPrincipal getUserPrincipalFromJWT(String token) {
        Claims claims = getClaims(token);
        return UserPrincipal.create(
            Long.parseLong(claims.getSubject()),
            claims.get("name").toString(),
            claims.get("username").toString(),
            claims.get("email").toString(),
            (List<String>) claims.get("roles"),
           null,
           // (Map<String, Object>) claims.get("sector"),
            claims.get("cpf").toString(),
            (Boolean) claims.get("firstAccess"),
            (LocalDateTime) claims.get("lastAccess"),
            Long.parseLong(claims.get("app").toString())
        );
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String validateToken(String authToken) {
        String msgError = "";

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        } catch (SignatureException ex) {
            msgError = "Assinatura de token inválida";
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            msgError = "Token inválido";
        } catch (ExpiredJwtException ex) {
            msgError = "Token expirado";
        } finally {
            if (!StringUtils.isEmpty(msgError)) {
                logger.error(msgError);
            }
        }

        return msgError;
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        return (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) ?  bearerToken.substring(7) : null;
    }

}
