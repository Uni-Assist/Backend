package com.example.UniAssist.model.dto;

import com.example.UniAssist.type.Role;

public class JwtResponse {
    private final String type = "Bearer";
    private String token;
    private Role role;
    private String unit;
    private String fullName;

    public JwtResponse () {}

    public JwtResponse(String token, Role role, String unit, String fullName) {
        this.token = token;
        this.role = role;
        this.unit = unit;
        this.fullName = fullName;
    }

    public String getType() {
        return type;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}