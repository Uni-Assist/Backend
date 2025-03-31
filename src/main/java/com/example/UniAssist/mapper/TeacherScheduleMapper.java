package com.example.UniAssist.mapper;

import com.example.UniAssist.model.dto.TeacherScheduleDTO;

public class TeacherScheduleMapper {
    public static TeacherScheduleDTO toDTO(TeacherScheduleDTO entity, String taskHeader, String groupName) {
        TeacherScheduleDTO dto = new TeacherScheduleDTO();
        dto.setId(entity.getId());
        dto.setSubjectName(entity.getSubjectName());
        dto.setGroupId(entity.getGroupId());
        dto.setGroupName(groupName);
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setClassroom(entity.getClassroom());
        dto.setType(entity.getType());
        dto.setHeader(taskHeader);
        return dto;
    }
}