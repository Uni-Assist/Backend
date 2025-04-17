package com.example.UniAssist.mapper;

import com.example.UniAssist.model.dto.FullNameDTO;

public class FullNameMapper {
    public static FullNameDTO toDTO(String lastName, String firstName, String middleName) {
        FullNameDTO dto = new FullNameDTO();
        dto.setLastName(lastName);
        dto.setFirstName(firstName);
        dto.setMiddleName(middleName);
        return dto;
    }
}