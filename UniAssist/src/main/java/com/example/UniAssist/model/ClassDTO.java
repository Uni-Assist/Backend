package com.example.UniAssist.model;
import com.example.UniAssist.type.ClassType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ClassDTO {

    public static class StudentSchedule {
        private UUID id;
        private String subject;
        private UUID teacherId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private ClassType type;
        private String classroom;
        private LocalDate date;

        public static StudentSchedule fromEntity(Class entity) {
            StudentSchedule dto = new StudentSchedule();
            dto.id = entity.getId();
            dto.subject = entity.getSubject();
            dto.teacherId = entity.getTeacherId();
            dto.startTime = entity.getStartTime();
            dto.endTime = entity.getEndTime();
            dto.type = entity.getType();
            dto.classroom = entity.getClassroom();
            dto.date = entity.getDate();
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

    public static class TeacherSchedule {
        private UUID id;
        private String subject;
        private UUID groupId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private ClassType type;
        private String classroom;
        private LocalDate date;

        public static TeacherSchedule fromEntity(Class entity) {
            TeacherSchedule dto = new TeacherSchedule();
            dto.id = entity.getId();
            dto.subject = entity.getSubject();
            dto.groupId = entity.getGroupId();
            dto.startTime = entity.getStartTime();
            dto.endTime = entity.getEndTime();
            dto.type = entity.getType();
            dto.classroom = entity.getClassroom();
            dto.date = entity.getDate();
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
}
