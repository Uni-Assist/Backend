package com.example.UniAssist.model.dto;
import com.example.UniAssist.type.SubjectType;
import com.example.UniAssist.model.entity.Group;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class GroupDTO {
    private UUID id;
    private String name;

    public static GroupDTO fromEntity(Group entity) {
        GroupDTO dto = new GroupDTO();
        dto.id = entity.getId();
        dto.name = entity.getName();
        return dto;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
