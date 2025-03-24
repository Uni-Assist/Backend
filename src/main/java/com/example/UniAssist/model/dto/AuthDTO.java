package com.example.UniAssist.model.dto;

import com.example.UniAssist.type.Department;

import java.util.UUID;

public class AuthDTO {
    private UUID id;
    private String password;
    private UUID groupId;
    private Department department;
    private String middleName;
    private String lastName;
    private String firstName;

    public AuthDTO() {}

    public AuthDTO(UUID id, String password, UUID groupId, Department department, String middleName, String lastName, String firstName) {
        this.id = id;
        this.password = password;
        this.groupId = groupId;
        this.department = department;
        this.middleName = middleName;
        this.lastName = lastName;
        this.firstName = firstName;
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

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
