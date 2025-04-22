package com.example.UniAssist.repository;

import com.example.UniAssist.model.dto.StudentSolutionDTO;
import com.example.UniAssist.model.dto.TeacherSolutionDTO;
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
        SELECT new com.example.UniAssist.model.dto.TeacherSolutionDTO(
            s.id, s.studentId, s.body, s.mark
        )
        FROM Solution s
        WHERE s.taskId = :taskId
    """)
    List<TeacherSolutionDTO> findSolutionsByTaskId(@Param("taskId") UUID taskId);

    @Query("""
        SELECT new com.example.UniAssist.model.dto.StudentSolutionDTO(
            s.id, s.studentId, s.body, s.mark
        )
        FROM Solution s
        WHERE s.taskId = :taskId
    """)
    StudentSolutionDTO findSolutionByTaskId(@Param("taskId") UUID taskId);
}