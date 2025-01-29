package com.example.UniAssist.model.dto;

import java.util.UUID;

public class TeacherDTO {
    private UUID teacherId;
    private String role;

    public TeacherDTO(UUID teacherId, String role) {
        this.teacherId = teacherId;
        this.role = role;
    }

    public UUID getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(UUID teacherId) {
        this.teacherId = teacherId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
