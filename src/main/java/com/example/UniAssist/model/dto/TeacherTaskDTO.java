package com.example.UniAssist.model.dto;

import java.util.List;
import java.util.UUID;

public class TeacherTaskDTO {
    private UUID id;
    private String header;
    private String body;
    private List<TeacherSolutionDTO> solutions;

    public TeacherTaskDTO() {}

    public TeacherTaskDTO(UUID id, String header, String body, List<TeacherSolutionDTO> solutions) {
        this.id = id;
        this.header = header;
        this.body = body;
        this.solutions = solutions;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public List<TeacherSolutionDTO> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<TeacherSolutionDTO> solutions) {
        this.solutions = solutions;
    }
}
