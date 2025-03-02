package com.example.UniAssist.controller;

import com.example.UniAssist.model.dto.TeacherLessonDTO;
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
    public ResponseEntity<TeacherLessonDTO> getTeacherLesson(
            @RequestParam("teacher_id") UUID teacherId,
            @RequestParam("lesson_id") UUID lessonId) {
        return ResponseEntity.ok(lessonService.getTeacherLesson(teacherId, lessonId));
    }
}
