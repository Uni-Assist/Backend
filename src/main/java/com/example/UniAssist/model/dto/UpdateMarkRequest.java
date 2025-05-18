package com.example.UniAssist.model.dto;

import java.util.UUID;

public class UpdateMarkRequest {
    private UUID solutionId;
    private Integer mark;

    public UpdateMarkRequest() {}

    public UpdateMarkRequest(UUID solutionId, Integer mark) {
        this.solutionId = solutionId;
        this.mark = mark;
    }

    public UUID getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(UUID solutionId) {
        this.solutionId = solutionId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }
}