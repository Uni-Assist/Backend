package com.example.UniAssist.service;

import com.example.UniAssist.mapper.FullNameMapper;
import com.example.UniAssist.model.dto.FullNameDTO;
import com.example.UniAssist.model.projection.FullNameProjection;
import com.example.UniAssist.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final FullNameMapper fullNameMapper;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository, FullNameMapper fullNameMapper) {
        this.teacherRepository = teacherRepository;
        this.fullNameMapper = fullNameMapper;
    }

    public Map<UUID, FullNameDTO> fetchFullNames(List<UUID> userIds) {
        List<FullNameProjection> rawFullNames = teacherRepository.findFullNamesByTeacherIds(userIds);
        return rawFullNames.stream()
                .collect(Collectors.toMap(FullNameProjection::getId, fullNameMapper::toDTO));
    }
}