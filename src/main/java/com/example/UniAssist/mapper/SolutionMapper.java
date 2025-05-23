package com.example.UniAssist.mapper;

import com.example.UniAssist.model.dto.SolutionDTO;
import com.example.UniAssist.model.dto.StudentSolutionRequest;
import com.example.UniAssist.model.entity.Solution;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SolutionMapper {
    SolutionDTO toDto(Solution solution);
    List<SolutionDTO> toDto(List<Solution> solutions);
    Solution toEntity(StudentSolutionRequest request, UUID studentId);
}
