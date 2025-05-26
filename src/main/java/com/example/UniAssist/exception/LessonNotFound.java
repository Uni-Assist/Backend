package com.example.UniAssist.exception;

public class LessonNotFound extends RuntimeException {
    public LessonNotFound() {
        super("Lesson not found");
    }
}
