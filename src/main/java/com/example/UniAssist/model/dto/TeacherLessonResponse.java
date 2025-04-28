package com.example.UniAssist.model.dto;

import java.util.List;

public class TeacherLessonResponse {
    private TeacherLessonDTO lesson;
    private TaskDTO task;
    private List<SolutionDTO> solutions;

    public TeacherLessonResponse() {}

    public TeacherLessonResponse(TeacherLessonDTO lesson, TaskDTO task, List<SolutionDTO> solutions) {
        this.lesson = lesson;
        this.task = task;
        this.solutions = solutions;
    }

    public TeacherLessonDTO getLesson() {
        return lesson;
    }

    public void setLesson(TeacherLessonDTO lesson) {
        this.lesson = lesson;
    }

    public TaskDTO getTask() {
        return task;
    }

    public void setTask(TaskDTO task) {
        this.task = task;
    }

    public List<SolutionDTO> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<SolutionDTO> solutions) {
        this.solutions = solutions;
    }
}
