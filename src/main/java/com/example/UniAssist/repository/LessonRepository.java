package com.example.UniAssist.repository;

import com.example.UniAssist.model.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, String> {

    @Query("SELECT c FROM Lesson c WHERE c.groupId = :groupId AND c.date = :date")
    List<Lesson> findLessonsByGroupAndDate(@Param("groupId") UUID groupId, @Param("date") LocalDate date);

    @Query("SELECT c FROM Lesson c WHERE c.teacherId = :teacherId AND c.date = :date")
    List<Lesson> findLessonsByTeacherAndDate(@Param("teacherId") UUID teacherId, @Param("date") LocalDate date);
}