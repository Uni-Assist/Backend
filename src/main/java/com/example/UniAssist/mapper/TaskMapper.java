package com.example.UniAssist.mapper;

import com.example.UniAssist.model.dto.TaskDTO;
import com.example.UniAssist.model.dto.TeacherTaskRequest;
import com.example.UniAssist.model.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
    TaskDTO toDTO(Task task);
    Task toEntity(TeacherTaskRequest request, UUID teacherId);
}
