package com.example.UniAssist.model.dto;

import com.example.UniAssist.type.LessonType;

import java.time.LocalDate;
import java.time.LocalTime;

public class TeacherLessonDTO {
    private String subjectName;
    private String groupName;
    private LocalTime startTime;
    private LocalTime endTime;
    private String classroom;
    private LocalDate date;
    private LessonType type;

    public TeacherLessonDTO() {}

    public TeacherLessonDTO(String subjectName, String groupName, LocalTime startTime, LocalTime endTime, String classroom, LocalDate date, LessonType type) {
        this.subjectName = subjectName;
        this.groupName = groupName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classroom = classroom;
        this.date = date;
        this.type = type;
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
}
