package com.example.UniAssist.exception;

public class SolutionAlreadyExists extends RuntimeException {
    public SolutionAlreadyExists() {
        super("Solution already exists");
    }
}
