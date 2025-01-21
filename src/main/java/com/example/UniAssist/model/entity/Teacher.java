package com.example.UniAssist.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @Column(name = "teacher_id", updatable = false, nullable = false)
    private UUID teacherId;

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
    private LocalDate date;

    @Column(name = "department_id", nullable = false)
    private UUID departmentId;

    @Column(name = "department", length = 30, nullable = false)
    private String department;

    @Column(name = "job_title", length = 30, nullable = false)
    private String jobTitle;

    public UUID getTeacherId() {
        return teacherId;
    }

    public void setStudentId(UUID teacherId) {
        this.teacherId = teacherId;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public UUID getDepartmentId() {
        return departmentId;
    }

    public void setGroupId(UUID departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
