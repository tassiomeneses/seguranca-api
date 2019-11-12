package com.example.seguranca.controller;


import com.example.seguranca.business.AuthBusiness;
import com.example.seguranca.payload.LoginRequest;
import com.example.seguranca.payload.UsuarioRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthBusiness business;

    @PostMapping("/signin")
    public ResponseEntity authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(business.authenticate(loginRequest));
    }

//    @PostMapping("/reset-password")
//    public ResponseEntity resetPassword(@Valid @RequestBody UsuarioRequest usuarioRequest) {
//        return ResponseEntity.ok(business.resetPassword(usuarioRequest));
//    }
//
//    @PostMapping("/forgot-password")
//    public ResponseEntity forgotPassword(@RequestBody UsuarioRequest usuarioRequest) {
//        business.forgotPassword(usuarioRequest);
//        return ResponseEntity.noContent().build();
//    }
}
