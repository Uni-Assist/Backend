package com.example.UniAssist.mapper;

import com.example.UniAssist.model.dto.AuthDTO;
import com.example.UniAssist.model.entity.Student;
import com.example.UniAssist.model.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {
    AuthDTO toDTO(Teacher teacher);
    AuthDTO toDTO(Student student);
}
