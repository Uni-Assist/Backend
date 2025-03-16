package com.example.UniAssist.controller;

import com.example.UniAssist.model.dto.UpdateMarkRequest;
import com.example.UniAssist.service.TeacherResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/response")
public class TeacherResponseController {

    private final TeacherResponseService teacherResponseService;

    @Autowired
    public TeacherResponseController(TeacherResponseService teacherResponseService) {this.teacherResponseService = teacherResponseService;}

    @PatchMapping("/teacher")
    public ResponseEntity<String> updateResponseMark(
            @RequestBody UpdateMarkRequest request) {
        String result = teacherResponseService.updateResponseMark(request.getId(), request.getMark());
        return ResponseEntity.ok(result);
    }
}