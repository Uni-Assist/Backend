package com.example.UniAssist.model.dto;

import com.example.UniAssist.type.LessonType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class StudentLessonDTO {
    private String subjectName;
    private UUID teacherId;
    private FullNameDTO fullName;
    private LocalTime startTime;
    private LocalTime endTime;
    private String classroom;
    private LocalDate date;
    private LessonType type;
    private StudentTaskDTO task;

    public StudentLessonDTO() {}

    public StudentLessonDTO(String subjectName, UUID teacherId, FullNameDTO fullName, LocalTime startTime, LocalTime endTime, String classroom, LocalDate date, LessonType type, StudentTaskDTO task) {
        this.subjectName = subjectName;
        this.teacherId = teacherId;
        this.fullName = fullName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classroom = classroom;
        this.date = date;
        this.type = type;
        this.task = task;
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

    public FullNameDTO getFullName() {
        return fullName;
    }

    public void setFullName(FullNameDTO fullName) {
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

    public StudentTaskDTO getTask() {
        return task;
    }

    public void setTask(StudentTaskDTO task) {
        this.task = task;
    }
}
