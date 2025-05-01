package com.example.UniAssist.mapper;

import com.example.UniAssist.model.dto.SolutionDTO;
import com.example.UniAssist.model.entity.Solution;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SolutionMapper {
    SolutionDTO toDto(Solution solution);
    List<SolutionDTO> toDto(List<Solution> solutions);
}
