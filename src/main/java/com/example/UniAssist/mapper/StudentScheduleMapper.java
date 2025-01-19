package com.example.UniAssist.mapper;

import com.example.UniAssist.model.dto.StudentScheduleDTO;
import com.example.UniAssist.model.entity.Subject;

public class StudentScheduleMapper {
    public static StudentScheduleDTO toDTO(Subject entity, String taskHeader) {
        StudentScheduleDTO dto = new StudentScheduleDTO();
        dto.setId(entity.getId());
        dto.setSubjectName(entity.getSubjectName());
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
