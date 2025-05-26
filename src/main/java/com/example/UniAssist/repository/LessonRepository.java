package com.example.UniAssist.repository;

import com.example.UniAssist.model.entity.Lesson;
import com.example.UniAssist.model.projection.LessonProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    @Query("""
        SELECT
            sub.name as subjectName,
            s.groupId as groupId,
            s.teacherId as teacherId,
            l.startTime as startTime,
            l.endTime as endTime,
            l.classroom as classroom,
            s.type as type
        FROM Lesson l
        JOIN Schedule s ON l.scheduleId = s.id
        JOIN Subject sub ON s.subjectId = sub.id
        WHERE l.id = :lessonId
    """)
    LessonProjection findLessonProjectionById(@Param("lessonId") UUID lessonId);
}