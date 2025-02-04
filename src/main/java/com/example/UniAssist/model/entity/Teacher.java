package com.example.UniAssist.model.entity;

import com.example.UniAssist.type.Department;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "fathername", length = 15)
    private String fatherName;

    @Column(name = "surname", length = 15)
    private String surName;

    @Column(name = "name", length = 15, nullable = false)
    private String name;

    @Column(name = "login", length = 15, nullable = false)
    private String login;

    @Column(name = "password", length = 15, nullable = false)
    private String password;

    @Column(name = "phone", nullable = false)
    private int phone;

    @Column(name = "email", length = 30, nullable = false)
    private String email;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "job_title", length = 40, nullable = false)
    private String jobTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "department", nullable = false)
    private Department department;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
