package com.example.UniAssist.service;

import com.example.UniAssist.model.entity.Response;
import com.example.UniAssist.mapper.ResponseMapper;
import com.example.UniAssist.repository.ResponseRepository;
import com.example.UniAssist.type.ResponseType;
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

    public String processStudentResponse(UUID studentId, UUID taskId, String body, ResponseType type) {
        Response response = new Response();
        response.setStudentId(studentId);
        response.setTaskId(taskId);
        response.setBody(body);
        response.setType(type);

        responseRepository.save(response);
        return "";
    }

    public String updateResponseMark(UUID id, Integer mark) {
        return responseRepository.findById(id)
                .map(response -> ResponseMapper.updateMark(response, mark))
                .map(responseRepository::save)
                .map(updated -> "")
                .orElse("");
    }
}