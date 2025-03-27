package com.example.UniAssist.service;

import com.example.UniAssist.model.dto.StudentResponseRequest;
import com.example.UniAssist.model.dto.UpdateMarkRequest;
import com.example.UniAssist.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ResponseService {

    private final ResponseRepository responseRepository;

    @Autowired
    public ResponseService(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    public String processStudentResponse(UUID studentId, StudentResponseRequest request) {
        responseRepository.saveResponse(studentId, request.getTaskId(), request.getBody(), request.getType());

        return "";
    }

    public String updateResponseMark(UpdateMarkRequest request) {
        responseRepository.updateMark(request.getId(), request.getMark());

        return "";
    }
}