package com.example.UniAssist.repository;

import com.example.UniAssist.model.entity.Group;
import com.example.UniAssist.projection.GroupNameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID> {

    @Query("SELECT g.id AS groupId, g.name AS groupName FROM Group g WHERE g.id IN :Ids")
    List<GroupNameProjection> findGroupNamesByIds(@Param("Ids") List<UUID> Ids);

    @Query("SELECT g.name FROM Group g WHERE g.id = :groupId")
    String findGroupNameById(@Param("groupId") UUID groupId);
}
