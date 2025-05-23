package com.example.UniAssist.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public class UpdateMarkRequest {

    @NotNull
    private UUID solutionId;

    @NotNull
    @PositiveOrZero
    @Max(100)
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