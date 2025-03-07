package com.example.UniAssist.model.dto;

import java.util.UUID;

public class AuthDTO {
    private UUID id;
    private String password;

    public AuthDTO() {}

    public AuthDTO(UUID id, String password) {
        this.id = id;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
