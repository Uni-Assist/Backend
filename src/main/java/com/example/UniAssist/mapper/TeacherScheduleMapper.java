package com.example.UniAssist.mapper;

import com.example.UniAssist.model.dto.TeacherScheduleDTO;

public class TeacherScheduleMapper {
    public static TeacherScheduleDTO toDTO(TeacherScheduleDTO entity, String taskHeader) {
        TeacherScheduleDTO dto = new TeacherScheduleDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
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