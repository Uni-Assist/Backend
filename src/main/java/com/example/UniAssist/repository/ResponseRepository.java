package com.example.UniAssist.repository;

import com.example.UniAssist.model.entity.Response;
import com.example.UniAssist.type.ResponseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface ResponseRepository extends JpaRepository<Response, UUID> {

    @Modifying
    @Transactional
    @Query("INSERT INTO Response (studentId, taskId, body, type) VALUES (:studentId, :taskId, :body, :type)")
    void saveResponse(@Param("studentId") UUID studentId, @Param("taskId") UUID taskId, @Param("body") String body, @Param("type") ResponseType type);

    @Modifying
    @Transactional
    @Query("UPDATE Response r SET r.mark=:mark WHERE r.id=:id")
    void updateMark(@Param("id") UUID id, @Param("mark") int mark);
}