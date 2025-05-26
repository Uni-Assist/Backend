package com.example.UniAssist.exception;

public class ScheduleNotFound extends RuntimeException {
    public ScheduleNotFound() {
        super("No lessons found");
    }
}