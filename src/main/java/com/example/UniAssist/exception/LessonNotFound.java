package com.example.UniAssist.exception;

public class LessonNotFound extends RuntimeException {
    public LessonNotFound(String message) {
        super(message);
    }
}
