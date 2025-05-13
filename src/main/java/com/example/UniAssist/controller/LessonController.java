package com.example.UniAssist.controller;

import com.example.UniAssist.model.dto.StudentLessonResponse;
import com.example.UniAssist.model.dto.TeacherLessonResponse;
import com.example.UniAssist.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<StudentLessonResponse> getStudentLesson(@PathVariable("lessonId") UUID lessonId) {
        return ResponseEntity.ok(lessonService.getStudentLesson(lessonId));
    }
}
