package com.example.UniAssist.repository;

import com.example.UniAssist.model.dto.StudentResponseDTO;
import com.example.UniAssist.model.dto.TeacherResponseDTO;
import com.example.UniAssist.model.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResponseRepository extends JpaRepository<Response, UUID> {

    @Query("""
        SELECT new com.example.UniAssist.model.dto.TeacherResponseDTO(
            r.id, r.studentId, r.body, r.mark
        )
        FROM Response r
        WHERE r.taskId = :taskId
    """)
    List<TeacherResponseDTO> findResponsesByTaskId(@Param("taskId") UUID taskId);

    @Query("""
        SELECT new com.example.UniAssist.model.dto.StudentResponseDTO(
            r.id, r.studentId, r.body, r.mark
        )
        FROM Response r
        WHERE r.taskId = :taskId
    """)
    StudentResponseDTO findResponseByTaskId(@Param("taskId") UUID taskId);
}