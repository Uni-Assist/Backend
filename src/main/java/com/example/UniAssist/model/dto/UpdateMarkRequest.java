package com.example.UniAssist.model.dto;

import java.util.UUID;

public class UpdateMarkRequest {
    private UUID id;
    private int mark;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}