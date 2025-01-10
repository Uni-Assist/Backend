package com.example.UniAssist.controller;

import com.example.UniAssist.repository.GroupRepository;
import com.example.UniAssist.model.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class GroupController {

    private GroupRepository groupRepository;

    @Autowired
    public GroupController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @GetMapping("/groups")
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
}
