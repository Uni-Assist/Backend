package com.example.UniAssist.model.dto;
import com.example.UniAssist.type.LessonType;

import java.time.LocalTime;
import java.util.UUID;

public class StudentScheduleDTO {
    private UUID id;
    private String subjectName;
    private UUID teacherId;
    private String fullName;
    private LocalTime startTime;
    private LocalTime endTime;
    private String classroom;
    private LessonType type;
    private String header;

    public StudentScheduleDTO() {}

    public StudentScheduleDTO(UUID id, String subjectName, UUID teacherId, String fullName, LocalTime startTime, LocalTime endTime, String classroom, LessonType type, String header) {
        this.id = id;
        this.subjectName = subjectName;
        this.teacherId = teacherId;
        this.fullName = fullName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classroom = classroom;
        this.type = type;
        this.header = header;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public UUID getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(UUID teacherId) {
        this.teacherId = teacherId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
