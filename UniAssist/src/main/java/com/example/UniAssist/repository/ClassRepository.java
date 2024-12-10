package com.example.UniAssist.repository;

import com.example.UniAssist.model.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ClassRepository extends JpaRepository<Class, String> {

    @Query("SELECT c FROM Class c WHERE c.groupId = :groupId AND c.date = :date")
    List<Class> findClassesByGroupAndDate(@Param("groupId") UUID groupId, @Param("date") LocalDate date);

    @Query("SELECT c FROM Class c WHERE c.teacherId = :teacherId AND c.date = :date")
    List<Class> findClassesByTeacherAndDate(@Param("teacherId") UUID teacherId, @Param("date") LocalDate date);
}