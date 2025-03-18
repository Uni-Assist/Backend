package com.example.UniAssist.repository;

import com.example.UniAssist.model.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID> {

}
