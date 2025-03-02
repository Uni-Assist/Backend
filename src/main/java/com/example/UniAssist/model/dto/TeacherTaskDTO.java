package com.example.UniAssist.model.dto;

import java.util.List;
import java.util.UUID;

public class TeacherTaskDTO {
    private UUID id;
    private String header;
    private String body;
    private List<TeacherResponseDTO> responses;

    public TeacherTaskDTO() {}

    public TeacherTaskDTO(UUID id, String header, String body, List<TeacherResponseDTO> responses) {
        this.id = id;
        this.header = header;
        this.body = body;
        this.responses = responses;
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

    public List<TeacherResponseDTO> getResponses() {
        return responses;
    }

    public void setResponses(List<TeacherResponseDTO> responses) {
        this.responses = responses;
    }
}
