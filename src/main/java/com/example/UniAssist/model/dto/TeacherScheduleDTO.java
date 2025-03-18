package com.example.UniAssist.model.dto;
import com.example.UniAssist.type.LessonType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class TeacherScheduleDTO {
    private UUID id;
    private String name;
    private UUID groupId;
    private LocalTime startTime;
    private LocalTime endTime;
    private String classroom;
    private LocalDate date;
    private LessonType type;
    private String header;

    public TeacherScheduleDTO() {}

    public TeacherScheduleDTO(UUID id, String name, UUID groupId, LocalTime startTime, LocalTime endTime, String classroom, LocalDate date, LessonType type, String header) {
        this.id = id;
        this.name = name;
        this.groupId = groupId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classroom = classroom;
        this.date = date;
        this.type = type;
        this.header = header;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LessonType getType() {
        return type;
    }

    public void setType(LessonType type) {
        this.type = type;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
