package com.example.UniAssist.mapper;

import com.example.UniAssist.model.dto.GroupDTO;
import com.example.UniAssist.model.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GroupMapper {
    GroupDTO toDTO(Group entity);
}