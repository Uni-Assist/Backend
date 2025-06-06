package com.example.UniAssist.model.projection;

import com.example.UniAssist.type.LessonType;

import java.time.LocalTime;
import java.util.UUID;

public interface LessonProjection {
    String getSubjectName();
    UUID getGroupId();
    UUID getTeacherId();
    LocalTime getStartTime();
    LocalTime getEndTime();
    String getClassroom();
    LessonType getType();
}
