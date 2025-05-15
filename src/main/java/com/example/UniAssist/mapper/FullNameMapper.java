package com.example.UniAssist.mapper;

import com.example.UniAssist.model.dto.FullNameDTO;
import com.example.UniAssist.model.projection.FullNameProjection;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FullNameMapper {
    FullNameDTO toDTO(String lastName, String firstName, String middleName);
    FullNameDTO toDTO(FullNameProjection projection);
}