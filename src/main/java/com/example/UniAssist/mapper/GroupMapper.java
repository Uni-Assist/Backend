package com.example.UniAssist.mapper;

import com.example.UniAssist.model.dto.GroupDTO;
import com.example.UniAssist.model.entity.Group;

public class GroupMapper {
    public static GroupDTO toDTO(Group entity) {
        GroupDTO dto = new GroupDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}