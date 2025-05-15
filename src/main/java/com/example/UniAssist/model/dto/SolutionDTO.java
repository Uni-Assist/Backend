package com.example.UniAssist.model.dto;

import java.util.UUID;

public class SolutionDTO {
    private UUID id;
    private String body;
    private Integer mark;

    public SolutionDTO() {}

    public SolutionDTO(UUID id, String body, Integer mark) {
        this.id = id;
        this.body = body;
        this.mark = mark;
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
}
