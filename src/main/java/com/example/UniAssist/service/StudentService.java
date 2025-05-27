package com.example.UniAssist.service;

import com.example.UniAssist.mapper.FullNameMapper;
import com.example.UniAssist.model.dto.FullNameDTO;
import com.example.UniAssist.model.projection.FullNameProjection;
import com.example.UniAssist.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final FullNameMapper fullNameMapper;

    @Autowired
    public StudentService(StudentRepository studentRepository, FullNameMapper fullNameMapper) {
        this.studentRepository = studentRepository;
        this.fullNameMapper = fullNameMapper;
    }

    public Map<UUID, FullNameDTO> fetchFullNames(List<UUID> userIds) {
        List<FullNameProjection> rawFullNames = studentRepository.findFullNamesByStudentIds(userIds);
        return rawFullNames.stream()
                .collect(Collectors.toMap(FullNameProjection::getId, fullNameMapper::toDTO));
    }
}
