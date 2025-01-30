package com.example.UniAssist.model.dto;

import java.util.UUID;

public class StudentDTO {
    private UUID studentId;

    public StudentDTO(UUID studentId) {
        this.studentId = studentId;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

}
