package com.example.UniAssist.repository;

import com.example.UniAssist.model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

    @Query("SELECT t.departmentId FROM Teacher t WHERE t.teacherId = :teacherId")
    UUID findDepartmentIdByTeacherId(@Param("teacherId") UUID teacherId);

    @Query("SELECT t FROM Teacher t WHERE t.login = :login AND t.password = :password")
    Optional<Teacher> findByLoginAndPassword(@Param("login") String login, @Param("password") String password);
}
