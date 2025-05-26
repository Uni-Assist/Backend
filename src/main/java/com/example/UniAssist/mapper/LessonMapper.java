package com.example.UniAssist.mapper;

import com.example.UniAssist.model.dto.StudentLessonDTO;
import com.example.UniAssist.model.dto.TeacherLessonDTO;
import com.example.UniAssist.model.projection.FullNameProjection;
import com.example.UniAssist.model.projection.LessonProjection;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LessonMapper {
    StudentLessonDTO toDTO(LessonProjection projection, FullNameProjection fullName);
    TeacherLessonDTO toDTO(LessonProjection projection, String groupName);
}
