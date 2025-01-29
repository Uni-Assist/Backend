package com.example.UniAssist.model.dto;

import java.util.UUID;

public class StudentDTO {
    private UUID studentId;
    private String role;

    public StudentDTO(UUID studentId, String role) {
        this.studentId = studentId;
        this.role = role;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
