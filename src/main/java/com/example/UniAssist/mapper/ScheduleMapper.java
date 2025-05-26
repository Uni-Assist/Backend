package com.example.UniAssist.mapper;

import com.example.UniAssist.model.dto.FullNameDTO;
import com.example.UniAssist.model.dto.StudentScheduleDTO;
import com.example.UniAssist.model.dto.TeacherScheduleDTO;
import com.example.UniAssist.model.projection.ScheduleProjection;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ScheduleMapper {
    StudentScheduleDTO toDTO(ScheduleProjection projection, FullNameDTO fullName);
    TeacherScheduleDTO toDTO(ScheduleProjection projection, String groupName);
}