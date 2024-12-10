package com.example.UniAssist.exception;

public class ScheduleNotFound extends RuntimeException {
    public ScheduleNotFound(String message) {
        super(message);
    }
}