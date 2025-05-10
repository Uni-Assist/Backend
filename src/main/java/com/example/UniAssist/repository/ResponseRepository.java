package com.example.UniAssist.repository;

import com.example.UniAssist.model.entity.Solution;
import com.example.UniAssist.type.SolutionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface ResponseRepository extends JpaRepository<Solution, UUID> {

    @Modifying
    @Transactional
    @Query("INSERT INTO Solution (studentId, taskId, body, type) VALUES (:studentId, :taskId, :body, :type)")
    void saveResponse(@Param("studentId") UUID studentId, @Param("taskId") UUID taskId, @Param("body") String body, @Param("type") SolutionType type);

    @Modifying
    @Transactional
    @Query("UPDATE Solution r SET r.mark=:mark WHERE r.id=:id")
    void updateMark(@Param("id") UUID id, @Param("mark") int mark);
}