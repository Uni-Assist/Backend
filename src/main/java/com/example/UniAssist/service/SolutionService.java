package com.example.UniAssist.service;

import com.example.UniAssist.mapper.SolutionMapper;
import com.example.UniAssist.model.dto.SolutionDTO;
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
    public SolutionService(SolutionRepository solutionRepository, SolutionMapper solutionMapper) {
        this.solutionRepository = solutionRepository;
        this.solutionMapper = solutionMapper;
    }

    public List<SolutionDTO> getSolutionsByTaskId(UUID taskId) {
        List<Solution> solutionEntities = solutionRepository.findSolutionsByTaskId(taskId);
        return solutionMapper.toDto(solutionEntities);
    }

    public SolutionDTO getSolutionByTaskId(UUID taskId) {
        Solution solutionEntity = solutionRepository.findSolutionByTaskId(taskId);
        return solutionMapper.toDto(solutionEntity);
    }
}
