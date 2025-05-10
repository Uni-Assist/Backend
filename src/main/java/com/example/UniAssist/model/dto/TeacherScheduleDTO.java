package com.example.UniAssist.model.dto;

import com.example.UniAssist.type.LessonType;

import java.time.LocalTime;
import java.util.UUID;

public class TeacherScheduleDTO {
    private UUID id;
    private String subjectName;
    private String groupName;
    private LocalTime startTime;
    private LocalTime endTime;
    private String classroom;
    private LessonType type;
    private String header;

    public TeacherScheduleDTO() {}

    public TeacherScheduleDTO(UUID id, String subjectName, String groupName, LocalTime startTime, LocalTime endTime, String classroom, LessonType type, String header) {
        this.id = id;
        this.subjectName = subjectName;
        this.groupName = groupName;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
