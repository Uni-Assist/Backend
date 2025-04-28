package com.example.UniAssist.repository;

import com.example.UniAssist.model.dto.SolutionDTO;
import com.example.UniAssist.model.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, UUID> {

    @Query("""
        SELECT new com.example.UniAssist.model.dto.SolutionDTO(
            s.id, s.studentId, s.body, s.mark
        )
        FROM Solution s
        WHERE s.taskId = :taskId
    """)
    List<SolutionDTO> findSolutionsByTaskId(@Param("taskId") UUID taskId);

    @Query("""
        SELECT new com.example.UniAssist.model.dto.SolutionDTO(
            s.id, s.studentId, s.body, s.mark
        )
        FROM Solution s
        WHERE s.taskId = :taskId
    """)
    SolutionDTO findSolutionByTaskId(@Param("taskId") UUID taskId);
}