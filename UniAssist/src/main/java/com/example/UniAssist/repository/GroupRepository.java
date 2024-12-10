package com.example.UniAssist;

import com.example.UniAssist.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID> {

}
