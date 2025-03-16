package com.example.UniAssist.service;

import com.example.UniAssist.model.entity.Response;
import com.example.UniAssist.type.ResponseType;
import com.example.UniAssist.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StudentResponseService {

    private final ResponseRepository responseRepository;

    @Autowired
    public StudentResponseService(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    public String processStudentResponse(UUID studentId, UUID taskId, String body, ResponseType type) {
        Response response = new Response();
        response.setStudentId(studentId);
        response.setTaskId(taskId);
        response.setBody(body);
        response.setType(type);
        responseRepository.save(response);
        return "Успех. ID ответа: " + response.getId();
    }
}