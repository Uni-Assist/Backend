package com.example.UniAssist.service;

import com.example.UniAssist.component.JwtProvider;
import com.example.UniAssist.model.dto.AuthDTO;
import com.example.UniAssist.model.dto.JwtRequest;
import com.example.UniAssist.model.dto.JwtResponse;
import com.example.UniAssist.repository.StudentRepository;
import com.example.UniAssist.repository.TeacherRepository;
import com.example.UniAssist.type.Role;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthService(TeacherRepository teacherRepository, StudentRepository studentRepository, JwtProvider jwtProvider) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.jwtProvider = jwtProvider;
    }

    public JwtResponse login(JwtRequest authRequest) throws AuthException {
        AuthDTO userAuth = teacherRepository.findIdAndPasswordByLogin(authRequest.getLogin());
        JwtResponse jwtResponse = new JwtResponse();
        if (userAuth == null) {
            userAuth = studentRepository.findIdAndPasswordByLogin(authRequest.getLogin());
            jwtResponse.setRole(Role.STUDENT);
        } else {
            jwtResponse.setRole(Role.TEACHER);
        }
        if (userAuth == null) {
            throw new AuthException("Invalid login");
        }
        if (userAuth.getPassword().equals(authRequest.getPassword())) {
            final String token = jwtProvider.generateToken(userAuth.getId());
            jwtResponse.setToken(token);
            return jwtResponse;
        } else {
            throw new AuthException("Invalid password");
        }
    }
}