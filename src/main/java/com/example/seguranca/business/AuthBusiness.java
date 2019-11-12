package com.example.seguranca.business;



import com.example.seguranca.exception.RuntimeException;
import com.example.seguranca.modal.User;
import com.example.seguranca.payload.LoginRequest;
import com.example.seguranca.security.JwtTokenProvider;
import com.example.seguranca.security.UserPrincipal;
import com.example.seguranca.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthBusiness {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Transactional
    public Map<String, Object> authenticate(LoginRequest loginRequest) {
       // loginRequest.getApplication()
        request.getSession().setAttribute("app",1L );

        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authReq);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        User user = userService.findById((principal).getId()).orElseThrow(() -> new RuntimeException("error.user.notFound"));
        user.setLastAccess(LocalDateTime.now());
        userService.update(user);

        Map<String, Object> data = new HashMap<>();
        data.put("token", tokenProvider.generateToken(principal));
       // data.put("functionalities", functionalityService.getFunctionalities(functionalityService.getByUser(user.getId(), loginRequest.getApplication())));

        return data;
    }

}
