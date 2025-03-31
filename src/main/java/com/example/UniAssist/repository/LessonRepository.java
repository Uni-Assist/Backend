package com.example.UniAssist.repository;

import com.example.UniAssist.model.dto.StudentLessonDTO;
import com.example.UniAssist.model.dto.TeacherLessonDTO;
import com.example.UniAssist.model.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    @Query("""
        SELECT new com.example.UniAssist.model.dto.TeacherLessonDTO(
            sub.name, s.groupId, null, l.startTime, l.endTime, l.classroom, l.date, s.type, null
        )
        FROM Lesson l
        JOIN Schedule s ON l.scheduleId = s.id
        JOIN Subject sub ON s.subjectId = sub.id
        WHERE s.teacherId = :teacherId AND l.id = :lessonId
    """)
    TeacherLessonDTO findLessonByTeacherAndId(
            @Param("teacherId") UUID teacherId,
            @Param("lessonId") UUID lessonId
    );

    @Query("""
        SELECT new com.example.UniAssist.model.dto.StudentLessonDTO(
            sub.name, s.teacherId, null, l.startTime, l.endTime, l.classroom, l.date, s.type, null
        )
        FROM Lesson l
        JOIN Schedule s ON l.scheduleId = s.id
        JOIN Subject sub ON s.subjectId = sub.id
        WHERE s.groupId = :groupId AND l.id = :lessonId
    """)
    StudentLessonDTO findLessonByGroupAndId(
            @Param("groupId") UUID groupId,
            @Param("lessonId") UUID lessonId
    );
}