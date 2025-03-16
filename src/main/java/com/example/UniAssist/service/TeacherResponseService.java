package com.example.UniAssist.service;

import com.example.UniAssist.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TeacherResponseService {

    private final ResponseRepository responseRepository;

    @Autowired
    public TeacherResponseService(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    public String updateResponseMark(UUID id, Integer mark) {
        return responseRepository.findById(id)
                .map(response -> {
                    response.setMark(mark);
                    responseRepository.save(response);
                    return "Успешно.";
                })
                .orElse("Ответ с указанным ID не найден.");
    }
}