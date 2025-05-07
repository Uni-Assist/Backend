package com.example.UniAssist.service;

import com.example.UniAssist.component.JwtProvider;
import com.example.UniAssist.mapper.AuthMapper;
import com.example.UniAssist.mapper.FullNameMapper;
import com.example.UniAssist.model.dto.AuthDTO;
import com.example.UniAssist.model.dto.JwtRequest;
import com.example.UniAssist.model.dto.JwtResponse;
import com.example.UniAssist.model.entity.Student;
import com.example.UniAssist.model.entity.Teacher;
import com.example.UniAssist.repository.GroupRepository;
import com.example.UniAssist.repository.StudentRepository;
import com.example.UniAssist.repository.TeacherRepository;
import com.example.UniAssist.type.Role;
import com.example.UniAssist.exception.AuthenticationException;
// import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final JwtProvider jwtProvider;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;
    private final FullNameMapper fullNameMapper;

    @Autowired
    public AuthService(
            TeacherRepository teacherRepository,
            StudentRepository studentRepository,
            GroupRepository groupRepository,
            JwtProvider jwtProvider,
            AuthMapper authMapper,
            PasswordEncoder passwordEncoder,
            FullNameMapper fullNameMapper) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.jwtProvider = jwtProvider;
        this.authMapper = authMapper;
        this.passwordEncoder = passwordEncoder;
        this.fullNameMapper = fullNameMapper;
    }

    public JwtResponse login(JwtRequest authRequest) throws AuthenticationException {
        Teacher rawTeacherAuth = teacherRepository.findTeacherByLogin(authRequest.getLogin());
        JwtResponse jwtResponse = new JwtResponse();
        AuthDTO userAuth;
        if (rawTeacherAuth == null) {
            Student rawStudentAuth = studentRepository.findStudentByLogin(authRequest.getLogin());
            userAuth = authMapper.toDTO(rawStudentAuth);
            jwtResponse.setUnit(groupRepository.findGroupNameById(userAuth.getGroupId()));
            jwtResponse.setRole(Role.STUDENT);
        } else {
            userAuth = authMapper.toDTO(rawTeacherAuth);
            jwtResponse.setUnit(userAuth.getDepartment().toString());
            jwtResponse.setRole(Role.TEACHER);
        }
        if (userAuth == null) {
            throw new AuthenticationException("Invalid login");
        }
        if (passwordEncoder.matches(authRequest.getPassword(), userAuth.getPassword())) {
            final String token = jwtProvider.generateToken(userAuth.getId());
            jwtResponse.setToken(token);
            jwtResponse.setFullName(fullNameMapper.toDTO(userAuth.getLastName(), userAuth.getFirstName(), userAuth.getMiddleName()));
            return jwtResponse;
        } else {
            throw new AuthenticationException("Invalid password");
        }
    }
}