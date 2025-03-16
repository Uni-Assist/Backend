package com.example.UniAssist.controller;

import com.example.UniAssist.type.ResponseType;
import com.example.UniAssist.service.StudentResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/response")
public class StudentResponseController {

    private final StudentResponseService studentResponseService;

    @Autowired
    public StudentResponseController(StudentResponseService studentResponseService) {this.studentResponseService = studentResponseService;}

    @PostMapping("/student")
    public ResponseEntity<String> submitResponse(
            @RequestParam UUID studentId,
            @RequestParam UUID taskId,
            @RequestParam String body,
            @RequestParam ResponseType type) {
        String result = studentResponseService.processStudentResponse(studentId, taskId, body, type);
        return ResponseEntity.ok(result);
    }
}