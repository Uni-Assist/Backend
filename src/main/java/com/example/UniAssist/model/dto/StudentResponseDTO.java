package com.example.UniAssist.model.dto;

import java.util.UUID;

public class StudentResponseDTO {
    private UUID id;
    private UUID studentId;
    private String body;
    private Integer mark;

    public StudentResponseDTO() {}

    public StudentResponseDTO(UUID id, UUID studentId, String body, Integer mark) {
        this.id = id;
        this.studentId = studentId;
        this.body = body;
        this.mark = mark;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }
}
