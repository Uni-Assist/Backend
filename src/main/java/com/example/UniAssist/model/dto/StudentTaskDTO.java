package com.example.UniAssist.model.dto;

import java.util.UUID;

public class StudentTaskDTO {
    private UUID id;
    private String header;
    private String body;
    private StudentResponseDTO response;

    public StudentTaskDTO() {}

    public StudentTaskDTO(UUID id, String header, String body, StudentResponseDTO response) {
        this.id = id;
        this.header = header;
        this.body = body;
        this.response = response;
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

    public StudentResponseDTO getResponse() {
        return response;
    }

    public void setResponse(StudentResponseDTO response) {
        this.response = response;
    }
}
