package com.example.seguranca.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommonsJwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(CommonsJwtAuthenticationEntryPoint.class);

    @Autowired
    private CommonsJwtTokenProvider tokenProvider;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        String jwt = tokenProvider.getJwtFromRequest(httpServletRequest);
        String msgError = tokenProvider.validateToken(jwt);
        msgError = !StringUtils.isEmpty(msgError) ? msgError : e.getMessage();

        logger.error("Responding with unauthorized error. Message - {}", msgError);
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, msgError);
    }
}
