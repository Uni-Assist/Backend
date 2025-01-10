package com.example.UniAssist.model.dto;
import com.example.UniAssist.type.ClassType;
import com.example.UniAssist.model.entity.Class;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class TeacherScheduleDTO {
    private UUID id;
    private String subject;
    private UUID groupId;
    private LocalTime startTime;
    private LocalTime endTime;
    private String classroom;
    private LocalDate date;
    private ClassType type;
    private String header;

    public static TeacherScheduleDTO fromEntity(Class entity, String taskHeader) {
        TeacherScheduleDTO dto = new TeacherScheduleDTO();
        dto.id = entity.getId();
        dto.subject = entity.getSubject();
        dto.groupId = entity.getGroupId();
        dto.startTime = entity.getStartTime();
        dto.endTime = entity.getEndTime();
        dto.classroom = entity.getClassroom();
        dto.date = entity.getDate();
        dto.type = entity.getType();
        dto.header = taskHeader;
        return dto;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ClassType getType() {
        return type;
    }

    public void setType(ClassType type) {
        this.type = type;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
