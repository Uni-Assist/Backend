package com.example.UniAssist.mapper;

import com.example.UniAssist.model.dto.StudentScheduleDTO;
import com.example.UniAssist.model.entity.Lesson;

public class StudentScheduleMapper {
    public static StudentScheduleDTO toDTO(Lesson entity, String taskHeader) {
        StudentScheduleDTO dto = new StudentScheduleDTO();
        dto.setId(entity.getId());
        dto.setLessonName(entity.getLessonName());
        dto.setTeacherId(entity.getTeacherId());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setClassroom(entity.getClassroom());
        dto.setDate(entity.getDate());
        dto.setType(entity.getType());
        dto.setHeader(taskHeader);
        return dto;
    }
}
