package com.example.UniAssist.mapper;

import com.example.UniAssist.model.entity.Response;

public class ResponseMapper {
    public static Response updateMark(Response response, Integer mark) {
        response.setMark(mark);
        return response;
    }
}