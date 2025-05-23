package com.example.UniAssist.model.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ErrorResponseDTO {
    private String code;
    private String description;
    private List<String> messages;

    public ErrorResponseDTO(String code, String description, List<String> messages) {
        this.code = code;
        this.description = description;
        this.messages = messages;
    }

    public ErrorResponseDTO(HttpStatus status, String message) {
        this.code = Integer.toString(status.value());
        this.description = status.getReasonPhrase();
        this.messages = List.of(message);
    }

    public ErrorResponseDTO(HttpStatus status, List<String> messages) {
        this.code = Integer.toString(status.value());
        this.description = status.getReasonPhrase();
        this.messages = messages;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}