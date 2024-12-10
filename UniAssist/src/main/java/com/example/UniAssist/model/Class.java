package com.example.UniAssist.model;

import com.example.UniAssist.type.ClassType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "classes")
public class Class {

    @Id
    @JsonView({Views.WithoutTeacherId.class, Views.WithoutGroupId.class})
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @JsonView({Views.WithoutTeacherId.class, Views.WithoutGroupId.class})
    @Column(name = "subject", length = 40, nullable = false)
    private String subject;

    @JsonView(Views.WithoutTeacherId.class)
    @Column(name = "teacher_id", nullable = false)
    private UUID teacherId;

    @JsonView(Views.WithoutGroupId.class)
    @Column(name = "group_id", nullable = false)
    private UUID groupId;

    @JsonView({Views.WithoutTeacherId.class, Views.WithoutGroupId.class})
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @JsonView({Views.WithoutTeacherId.class, Views.WithoutGroupId.class})
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @JsonView({Views.WithoutTeacherId.class, Views.WithoutGroupId.class})
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ClassType type;

    @JsonView({Views.WithoutTeacherId.class, Views.WithoutGroupId.class})
    @Column(name = "classroom", length = 10, nullable = false)
    private String classroom;

    @JsonView({Views.WithoutTeacherId.class, Views.WithoutGroupId.class})
    @Column(name = "date", nullable = false)
    private LocalDate date;

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

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public ClassType getType() {
        return type;
    }

    public void setType(ClassType type) {
        this.type = type;
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
}
