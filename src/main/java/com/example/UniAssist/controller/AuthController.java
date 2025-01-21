package com.example.UniAssist.controller;

import com.example.UniAssist.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/teacher")
    public ResponseEntity<Map<String, Object>> teacherLogin(
            @RequestBody Map<String, String> credentials) {
        String login = credentials.get("login");
        String password = credentials.get("password");

        Map<String, Object> response = authService.authenticateTeacher(login, password);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/student")
    public ResponseEntity<Map<String, Object>> studentLogin(
            @RequestBody Map<String, String> credentials) {
        String login = credentials.get("login");
        String password = credentials.get("password");

        Map<String, Object> response = authService.authenticateStudent(login, password);
        return ResponseEntity.ok(response);
    }
}

