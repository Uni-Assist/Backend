package com.example.UniAssist.controller;

import com.example.UniAssist.model.dto.TeacherTaskRequest;
import com.example.UniAssist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/teacher")
    public ResponseEntity<Void> submitTask(
            @RequestBody TeacherTaskRequest request,
            @AuthenticationPrincipal UUID teacherId) {
        taskService.handleTeacherTask(request, teacherId);
        return ResponseEntity.ok().build();
    }
}
