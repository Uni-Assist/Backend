package com.example.UniAssist.repository;

import com.example.UniAssist.model.entity.Student;
import com.example.UniAssist.model.projection.FullNameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    @Query("SELECT s.groupId FROM Student s WHERE s.id = :studentId")
    UUID findGroupIdByStudentId(@Param("studentId") UUID studentId);

    @Query("SELECT t.id AS id, t.lastName AS lastName, t.middleName AS middleName, t.firstName AS firstName FROM Student t WHERE t.id IN :studentIds")
    List<FullNameProjection> findFullNamesByStudentIds(@Param("studentIds") List<UUID> studentIds);

    Student findStudentByLogin(String login);
}