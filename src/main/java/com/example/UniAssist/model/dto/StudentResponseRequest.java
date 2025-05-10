package com.example.UniAssist.model.dto;

import com.example.UniAssist.model.entity.Solution;
import com.example.UniAssist.type.SolutionType;
import java.util.UUID;

public class StudentResponseRequest {
    private UUID taskId;
    private String body;
    private SolutionType type;

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