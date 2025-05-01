package com.example.UniAssist.repository;

import com.example.UniAssist.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    @Query("SELECT s.groupId FROM Student s WHERE s.id = :studentId")
    UUID findGroupIdByStudentId(@Param("studentId") UUID studentId);

    Student findStudentByLogin(String login);
}