package com.example.UniAssist.model.dto;

import com.example.UniAssist.type.ResponseType;
import java.util.UUID;

public class StudentResponseRequest {
    private UUID taskId;
    private String body;
    private ResponseType type;

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

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }
}