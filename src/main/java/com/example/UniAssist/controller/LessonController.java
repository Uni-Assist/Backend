package com.example.UniAssist.controller;

import com.example.UniAssist.model.dto.StudentLessonResponse;
import com.example.UniAssist.model.dto.TeacherLessonResponse;
import com.example.UniAssist.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/lesson")
public class LessonController {

    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {this.lessonService = lessonService;}

    @GetMapping("/teacher/{lessonId}")
    public ResponseEntity<TeacherLessonResponse> getTeacherLesson(@PathVariable("lessonId") UUID lessonId) {
        return ResponseEntity.ok(lessonService.getTeacherLesson(lessonId));
    }

    @GetMapping("/student/{lessonId}")
    public ResponseEntity<StudentLessonResponse> getStudentLesson(
            @PathVariable("lessonId") UUID lessonId,
            @AuthenticationPrincipal UUID studentId) {
        return ResponseEntity.ok(lessonService.getStudentLesson(lessonId, studentId));
    }
}
