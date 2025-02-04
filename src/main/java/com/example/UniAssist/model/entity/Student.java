package com.example.UniAssist.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "middle_name", length = 15)
    private String middleName;

    @Column(name = "last_name", length = 15)
    private String lastName;

    @Column(name = "first_name", length = 15, nullable = false)
    private String firstName;

    @Column(name = "login", length = 15, nullable = false)
    private String login;

    @Column(name = "password", length = 15, nullable = false)
    private String password;

    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @Column(name = "email", length = 30, nullable = false)
    private String email;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "group_id", nullable = false)
    private UUID groupId;

    public UUID getStudentId() {
        return id;
    }

    public void setStudentId(UUID studentId) {
        this.id = studentId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDate() {
        return birthdate;
    }

    public void setDate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }
}
