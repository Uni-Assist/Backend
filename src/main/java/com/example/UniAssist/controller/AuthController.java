package com.example.UniAssist.controller;

import com.example.UniAssist.model.dto.JwtRequest;
import com.example.UniAssist.model.dto.JwtResponse;
import com.example.UniAssist.service.AuthService;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) throws AuthException {
        final JwtResponse jwtResponse = authService.login(jwtRequest);
        return ResponseEntity.ok(jwtResponse);
    }
}