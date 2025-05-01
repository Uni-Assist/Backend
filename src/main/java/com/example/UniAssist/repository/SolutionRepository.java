package com.example.UniAssist.repository;

import com.example.UniAssist.model.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, UUID> {

    List<Solution> findSolutionsByTaskId(UUID taskId);

    Solution findSolutionByTaskId(UUID taskId);
}