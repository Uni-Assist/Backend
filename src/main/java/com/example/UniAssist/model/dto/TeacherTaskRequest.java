package com.example.UniAssist.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class TeacherTaskRequest {
    @NotNull
    private UUID lessonId;

    @NotBlank
    private String header;

    @NotBlank
    private String body;

    @NotNull
    @Future
    private LocalDateTime dueTime;

    public TeacherTaskRequest() {}

    public TeacherTaskRequest(UUID lessonId, String header, String body, LocalDateTime dueTime) {
        this.lessonId = lessonId;
        this.header = header;
        this.body = body;
        this.dueTime = dueTime;
    }

    public UUID getLessonId() {
        return lessonId;
    }

    public void setLessonId(UUID lessonId) {
        this.lessonId = lessonId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getDueTime() {
        return dueTime;
    }

    public void setDueTime(LocalDateTime dueTime) {
        this.dueTime = dueTime;
    }
}