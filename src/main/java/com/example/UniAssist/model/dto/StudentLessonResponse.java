package com.example.UniAssist.model.dto;

public class StudentLessonResponse {
    private StudentLessonDTO lesson;
    private TaskDTO task;
    private SolutionDTO solution;

    public StudentLessonResponse() {}

    public StudentLessonResponse(StudentLessonDTO lesson, TaskDTO task, SolutionDTO solution) {
        this.lesson = lesson;
        this.task = task;
        this.solution = solution;
    }

    public StudentLessonDTO getLesson() {
        return lesson;
    }

    public void setLesson(StudentLessonDTO lesson) {
        this.lesson = lesson;
    }

    public TaskDTO getTask() {
        return task;
    }

    public void setTask(TaskDTO task) {
        this.task = task;
    }

    public SolutionDTO getSolution() {
        return solution;
    }

    public void setSolution(SolutionDTO solution) {
        this.solution = solution;
    }
}
