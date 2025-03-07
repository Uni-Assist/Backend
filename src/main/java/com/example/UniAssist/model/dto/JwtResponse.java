package com.example.UniAssist.model.dto;

import com.example.UniAssist.type.Role;

public class JwtResponse {
    private final String type = "Bearer";
    private String token;
    private Role role;

    public JwtResponse () {}

    public JwtResponse(String token, Role role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}