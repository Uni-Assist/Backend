package com.example.UniAssist.repository;

import com.example.UniAssist.model.entity.Task;
import com.example.UniAssist.projection.TaskHeaderProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query("SELECT t.lessonId AS lessonId, t.header AS header FROM Task t WHERE t.lessonId IN :lessonIds")
    List<TaskHeaderProjection> findTaskHeadersByLessonIds(@Param("lessonIds") List<UUID> lessonIds);
}