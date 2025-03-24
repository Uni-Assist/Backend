package com.example.UniAssist.controller;

import com.example.UniAssist.model.dto.StudentResponseRequest;
import com.example.UniAssist.model.dto.UpdateMarkRequest;
import com.example.UniAssist.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/response")
public class ResponseController {

    private final ResponseService responseService;

    @Autowired
    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @PostMapping("/student")
    public ResponseEntity<String> submitResponse(
            @RequestBody StudentResponseRequest request,
            @AuthenticationPrincipal String studentId) {
        String result = responseService.processStudentResponse(
                UUID.fromString(studentId),
                request.getTaskId(),
                request.getBody(),
                request.getType()
        );
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/teacher")
    public ResponseEntity<String> updateResponseMark(
            @RequestBody UpdateMarkRequest request) {
        String result = responseService.updateResponseMark(request.getId(), request.getMark());
        return ResponseEntity.ok(result);
    }
}