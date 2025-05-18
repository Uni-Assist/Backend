package com.example.UniAssist.repository;

import com.example.UniAssist.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    Task findTaskByLessonId(UUID lessonId);
}