package com.example.UniAssist.mapper;

import com.example.UniAssist.model.dto.FullNameDTO;
import com.example.UniAssist.model.dto.StudentSolutionDTO;
import com.example.UniAssist.model.dto.StudentSolutionRequest;
import com.example.UniAssist.model.dto.TeacherSolutionDTO;
import com.example.UniAssist.model.entity.Solution;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SolutionMapper {
    StudentSolutionDTO toDto(Solution solution);
    Solution toEntity(StudentSolutionRequest request, UUID studentId);

    static List<TeacherSolutionDTO> toDTO(List<Solution> solutions, Map<UUID, FullNameDTO> fullNames) {
        return solutions.stream()
                .filter(solution -> fullNames.containsKey(solution.getStudentId()))
                .map(solution -> new TeacherSolutionDTO(
                        solution.getId(),
                        solution.getBody(),
                        solution.getMark(),
                        fullNames.get(solution.getStudentId())
                ))
                .collect(Collectors.toList());
    }
}
