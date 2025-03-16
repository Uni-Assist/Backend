package com.example.UniAssist.model.dto;

import java.util.UUID;

public class UpdateMarkRequest {
    private UUID id;
    private Integer mark;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }
}