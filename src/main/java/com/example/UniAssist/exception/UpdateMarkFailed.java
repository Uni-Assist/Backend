package com.example.UniAssist.exception;

public class UpdateMarkFailed extends RuntimeException {
    public UpdateMarkFailed() {
        super("Failed to update mark");
    }
}
