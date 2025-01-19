package com.example.UniAssist.mapper;

import com.example.UniAssist.model.dto.TeacherScheduleDTO;
import com.example.UniAssist.model.entity.Subject;

public class TeacherScheduleMapper {
    public static TeacherScheduleDTO toDTO(Subject entity, String taskHeader) {
        TeacherScheduleDTO dto = new TeacherScheduleDTO();
        dto.setId(entity.getId());
        dto.setSubjectName(entity.getSubjectName());
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