package com.example.UniAssist.model.dto;

import com.example.UniAssist.type.SolutionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class StudentSolutionRequest {

    @NotNull
    private UUID taskId;

    @NotBlank
    private String body;

    @NotNull
    private SolutionType type;

    public StudentSolutionRequest() {}

    public StudentSolutionRequest(UUID taskId, String body, SolutionType type) {
        this.taskId = taskId;
        this.body = body;
        this.type = type;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public SolutionType getType() {
        return type;
    }

    public void setType(SolutionType type) {
        this.type = type;
    }
}