package com.example.UniAssist.model.dto;

public class StudentLessonResponse {
    private StudentLessonDTO lesson;
    private TaskDTO task;
    private StudentSolutionDTO solution;

    public StudentLessonResponse() {}

    public StudentLessonResponse(StudentLessonDTO lesson, TaskDTO task, StudentSolutionDTO solution) {
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

    public StudentSolutionDTO getSolution() {
        return solution;
    }

    public void setSolution(StudentSolutionDTO solution) {
        this.solution = solution;
    }
}
