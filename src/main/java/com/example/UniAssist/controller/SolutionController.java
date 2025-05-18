package com.example.UniAssist.controller;

import com.example.UniAssist.model.dto.StudentSolutionRequest;
import com.example.UniAssist.model.dto.UpdateMarkRequest;
import com.example.UniAssist.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/solution")
public class SolutionController {

    private final SolutionService solutionService;

    @Autowired
    public SolutionController(SolutionService solutionService) {
        this.solutionService = solutionService;
    }

    @PostMapping("/student")
    public ResponseEntity<String> submitResponse(
            @RequestBody StudentSolutionRequest request,
            @AuthenticationPrincipal UUID studentId) {
        String result = solutionService.handleStudentSolution(studentId, request);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/teacher")
    public ResponseEntity<String> updateResponseMark(
            @RequestBody UpdateMarkRequest request) {
        String result = solutionService.updateResponseMark(request);
        return ResponseEntity.ok(result);
    }
}