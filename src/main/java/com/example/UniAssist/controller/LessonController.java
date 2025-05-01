package com.example.UniAssist.controller;

import com.example.UniAssist.model.dto.StudentLessonResponse;
import com.example.UniAssist.model.dto.TeacherLessonResponse;
import com.example.UniAssist.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/lesson")
public class LessonController {

    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {this.lessonService = lessonService;}

    @GetMapping("/teacher")
    public ResponseEntity<TeacherLessonResponse> getTeacherLesson(@RequestParam("lesson_id") UUID lessonId) {
        return ResponseEntity.ok(lessonService.getTeacherLesson(lessonId));
    }

    @GetMapping("/student")
    public ResponseEntity<StudentLessonResponse> getStudentLesson(@RequestParam("lesson_id") UUID lessonId) {
        return ResponseEntity.ok(lessonService.getStudentLesson(lessonId));
    }
}
