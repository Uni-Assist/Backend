package com.example.UniAssist.model.dto;

import java.util.UUID;

public class TeacherDTO {
    private UUID teacherId;

    public TeacherDTO(UUID teacherId) {
        this.teacherId = teacherId;
    }

    public UUID getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(UUID teacherId) {
        this.teacherId = teacherId;
    }

}
