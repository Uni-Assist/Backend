package com.example.UniAssist.repository;

import com.example.UniAssist.model.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {

    @Query("SELECT c FROM Subject c WHERE c.groupId = :groupId AND c.date = :date")
    List<Subject> findSubjectsByGroupAndDate(@Param("groupId") UUID groupId, @Param("date") LocalDate date);

    @Query("SELECT c FROM Subject c WHERE c.teacherId = :teacherId AND c.date = :date")
    List<Subject> findSubjectsByTeacherAndDate(@Param("teacherId") UUID teacherId, @Param("date") LocalDate date);
}