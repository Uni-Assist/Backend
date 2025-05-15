package com.example.UniAssist.repository;

import com.example.UniAssist.model.entity.Schedule;
import com.example.UniAssist.model.projection.ScheduleProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {

    @Query("""
        SELECT
            l.id as id,
            sub.name as subjectName,
            s.teacherId as teacherId,
            l.startTime as startTime,
            l.endTime as endTime,
            l.classroom as classroom,
            s.type as type
        FROM Schedule s
        JOIN Lesson l ON s.id = l.scheduleId
        JOIN Subject sub ON s.subjectId = sub.id
        WHERE s.groupId = :groupId AND l.date = :date
    """)
    List<ScheduleProjection> findScheduleByGroupAndDate(
            @Param("groupId") UUID groupId,
            @Param("date") LocalDate date
    );

    @Query("""
        SELECT
            l.id as id,
            sub.name as subjectName,
            s.groupId as groupId,
            l.startTime as startTime,
            l.endTime as endTime,
            l.classroom as classroom,
            s.type as type
        FROM Schedule s
        JOIN Lesson l ON s.id = l.scheduleId
        JOIN Subject sub ON s.subjectId = sub.id
        WHERE s.teacherId = :teacherId AND l.date = :date
    """)
    List<ScheduleProjection> findScheduleByTeacherAndDate(
            @Param("teacherId") UUID teacherId,
            @Param("date") LocalDate date
    );
}