package com.example.UniAssist.model.dto;

import org.springframework.http.HttpStatus;

public class ErrorResponseDTO {
    private String code;
    private String description;
    private String message;

    public ErrorResponseDTO(String code, String description, String message) {
        this.code = code;
        this.message = message;
        this.description = description;
    }


    public ErrorResponseDTO(HttpStatus status, String message) {
        this.code = Integer.toString(status.value());
        this.description = status.getReasonPhrase();
        this.message = message;
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

    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}