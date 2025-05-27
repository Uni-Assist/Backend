package com.example.UniAssist.model.dto;

import java.util.UUID;

public class TeacherSolutionDTO {
    private UUID id;
    private String body;
    private Integer mark;
    private FullNameDTO fullName;

    public TeacherSolutionDTO() {}

    public TeacherSolutionDTO(UUID id, String body, Integer mark, FullNameDTO fullName) {
        this.id = id;
        this.body = body;
        this.mark = mark;
        this.fullName = fullName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public FullNameDTO getFullName() {
        return fullName;
    }

    public void setFullName(FullNameDTO fullName) {
        this.fullName = fullName;
    }
}
