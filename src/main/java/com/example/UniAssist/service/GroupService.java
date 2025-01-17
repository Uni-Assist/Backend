package com.example.UniAssist.service;

import com.example.UniAssist.model.dto.GroupDTO;
import com.example.UniAssist.repository.GroupRepository;
import com.example.UniAssist.model.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<GroupDTO> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream()
                .map(GroupDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
