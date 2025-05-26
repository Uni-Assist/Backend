package com.example.UniAssist.exception;

public class TaskAlreadyExists extends RuntimeException {
    public TaskAlreadyExists() {
        super("Task already exists");
    }
}
