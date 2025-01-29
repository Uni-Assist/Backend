package com.example.UniAssist.service;

import com.example.UniAssist.exception.AuthenticationException;
import com.example.UniAssist.repository.StudentRepository;
import com.example.UniAssist.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Map<String, Object> authenticateTeacher(String login, String password) {
        return teacherRepository.findByLoginAndPassword(login, password)
                .map(teacher -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("teacher_id", teacher.getTeacherId());
                    response.put("role", "teacher");
                    return response;
                })
                .orElseThrow(() -> new AuthenticationException("Invalid login or password for teacher"));
    }

    public Map<String, Object> authenticateStudent(String login, String password) {
        return studentRepository.findByLoginAndPassword(login, password)
                .map(student -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("student_id", student.getStudentId());
                    response.put("role", "student");
                    return response;
                })
                .orElseThrow(() -> new AuthenticationException("Invalid login or password for student"));
    }
}

