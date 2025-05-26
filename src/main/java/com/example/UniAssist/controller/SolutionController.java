package com.example.UniAssist.controller;

import com.example.UniAssist.model.dto.StudentSolutionRequest;
import com.example.UniAssist.model.dto.UpdateMarkRequest;
import com.example.UniAssist.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Void> submitSolution(
            @RequestBody StudentSolutionRequest request,
            @AuthenticationPrincipal UUID studentId) {
        solutionService.handleStudentSolution(studentId, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/teacher")
    public ResponseEntity<Void> updateSolutionMark(
            @RequestBody UpdateMarkRequest request) {
        solutionService.updateSolutionMark(request);
        return ResponseEntity.ok().build();
    }
}