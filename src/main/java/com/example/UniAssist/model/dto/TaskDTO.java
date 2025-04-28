package com.example.UniAssist.model.dto;

import java.util.UUID;

public class TaskDTO {
    private UUID id;
    private String header;
    private String body;

    public TaskDTO() {}

    public TaskDTO(UUID id, String header, String body) {
        this.id = id;
        this.header = header;
        this.body = body;
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
}
