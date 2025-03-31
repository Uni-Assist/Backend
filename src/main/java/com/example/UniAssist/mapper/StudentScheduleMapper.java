package com.example.UniAssist.mapper;

import com.example.UniAssist.model.dto.StudentScheduleDTO;

public class StudentScheduleMapper {
    public static StudentScheduleDTO toDTO(StudentScheduleDTO entity, String taskHeader, String fullName) {
        StudentScheduleDTO dto = new StudentScheduleDTO();
        dto.setId(entity.getId());
        dto.setSubjectName(entity.getSubjectName());
        dto.setFullName(fullName);
        dto.setTeacherId(entity.getTeacherId());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setClassroom(entity.getClassroom());
        dto.setType(entity.getType());
        dto.setHeader(taskHeader);
        return dto;
    }
}