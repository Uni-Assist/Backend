package com.example.UniAssist.exception;

public class SolutionNotFound extends RuntimeException {
    public SolutionNotFound() {
        super("Solution not found");
    }
}
