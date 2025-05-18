package com.example.UniAssist.repository;

import com.example.UniAssist.model.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, UUID> {
    List<Solution> findSolutionsByTaskId(UUID taskId);

    Solution findSolutionByTaskIdAndStudentId(UUID taskId, UUID studentId);

    boolean existsByStudentIdAndTaskId(UUID studentId, UUID taskId);

    @Modifying
    @Transactional
    @Query("UPDATE Solution s SET s.mark=:mark WHERE s.id=:id")
    void updateMark(@Param("id") UUID id, @Param("mark") Integer mark);

}