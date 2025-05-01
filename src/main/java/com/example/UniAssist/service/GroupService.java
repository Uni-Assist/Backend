package com.example.UniAssist.service;

import com.example.UniAssist.mapper.GroupMapper;
import com.example.UniAssist.model.dto.GroupDTO;
import com.example.UniAssist.model.entity.Group;
import com.example.UniAssist.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Autowired
    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    public List<GroupDTO> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream()
                .map(groupMapper::toDTO)
                .collect(Collectors.toList());
    }
}
