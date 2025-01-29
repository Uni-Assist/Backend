package com.example.UniAssist.controller;

import com.example.UniAssist.model.dto.StudentDTO;
import com.example.UniAssist.model.dto.TeacherDTO;
import com.example.UniAssist.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login/teacher")
    public ResponseEntity<TeacherDTO> teacherLogin(
            @RequestBody Map<String, String> credentials) {
        String login = credentials.get("login");
        String password = credentials.get("password");

        Map<String, Object> response = authService.authenticateTeacher(login, password);
        TeacherDTO teacherDTO = new TeacherDTO((UUID) response.get("teacher_id"), (String) response.get("role"));
        return ResponseEntity.ok(teacherDTO);
    }

    @PostMapping("/login/student")
    public ResponseEntity<StudentDTO> studentLogin(
            @RequestBody Map<String, String> credentials) {
        String login = credentials.get("login");
        String password = credentials.get("password");

        Map<String, Object> response = authService.authenticateStudent(login, password);
        StudentDTO studentDTO = new StudentDTO((UUID) response.get("student_id"), (String) response.get("role"));
        return ResponseEntity.ok(studentDTO);
    }
}