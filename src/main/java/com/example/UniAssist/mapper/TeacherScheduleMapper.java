package com.example.UniAssist.mapper;

import com.example.UniAssist.model.dto.TeacherScheduleDTO;
import com.example.UniAssist.model.entity.Lesson;

public class TeacherScheduleMapper {
    public static TeacherScheduleDTO toDTO(Lesson entity, String taskHeader) {
        TeacherScheduleDTO dto = new TeacherScheduleDTO();
        dto.setId(entity.getId());
        dto.setLessonName(entity.getLessonName());
        dto.setGroupId(entity.getGroupId());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setClassroom(entity.getClassroom());
        dto.setDate(entity.getDate());
        dto.setType(entity.getType());
        dto.setHeader(taskHeader);
        return dto;
    }
}