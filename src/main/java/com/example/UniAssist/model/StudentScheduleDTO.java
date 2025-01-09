package com.example.UniAssist.model;
import com.example.UniAssist.type.ClassType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;


public class StudentScheduleDTO {
    private UUID id;
    private String subject;
    private UUID teacherId;
    private LocalTime startTime;
    private LocalTime endTime;
    private String classroom;
    private LocalDate date;
    private ClassType type;
    private String header;

    public static StudentScheduleDTO fromEntity(Class entity, String taskHeader) {
        StudentScheduleDTO dto = new StudentScheduleDTO();
        dto.id = entity.getId();
        dto.subject = entity.getSubject();
        dto.teacherId = entity.getTeacherId();
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

    public UUID getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(UUID teacherId) {
        this.teacherId = teacherId;
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
