package com.example.UniAssist.service;

import com.example.UniAssist.exception.SolutionAlreadyExists;
import com.example.UniAssist.exception.SolutionNotFound;
import com.example.UniAssist.exception.UpdateMarkFailed;
import com.example.UniAssist.mapper.SolutionMapper;
import com.example.UniAssist.model.dto.SolutionDTO;
import com.example.UniAssist.model.dto.StudentSolutionRequest;
import com.example.UniAssist.model.dto.UpdateMarkRequest;
import com.example.UniAssist.model.entity.Solution;
import com.example.UniAssist.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SolutionService {

    private final SolutionRepository solutionRepository;
    private final SolutionMapper solutionMapper;

    @Autowired
    public SolutionService(
            SolutionRepository solutionRepository,
            SolutionMapper solutionMapper) {
        this.solutionRepository = solutionRepository;
        this.solutionMapper = solutionMapper;
    }

    public List<SolutionDTO> getSolutionsByTaskId(UUID taskId) {
        List<Solution> solutionEntities = solutionRepository.findSolutionsByTaskId(taskId);
        return solutionMapper.toDto(solutionEntities);
    }

    public SolutionDTO getSolutionByTaskId(UUID taskId, UUID studentId) {
        Solution solutionEntity = solutionRepository.findSolutionByTaskIdAndStudentId(taskId, studentId);
        return solutionMapper.toDto(solutionEntity);
    }

    public void handleStudentSolution(UUID studentId, StudentSolutionRequest request) {
        if (solutionRepository.existsByStudentIdAndTaskId(studentId, request.getTaskId())) {
            throw new SolutionAlreadyExists();
        }
        Solution solutionEntity = solutionMapper.toEntity(request, studentId);
        solutionRepository.save(solutionEntity);

    }

    public void updateResponseMark(UpdateMarkRequest request) {
        if (!solutionRepository.existsById(request.getSolutionId())) {
            throw new SolutionNotFound();
        }

        if (solutionRepository.updateMark(request.getSolutionId(), request.getMark()) != 1) {
            throw new UpdateMarkFailed();
        }
    }
}
