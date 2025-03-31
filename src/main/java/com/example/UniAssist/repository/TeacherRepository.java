package com.example.UniAssist.repository;

import com.example.UniAssist.model.entity.Teacher;
import com.example.UniAssist.projection.FullNameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

    @Query("SELECT t.id AS id, t.lastName AS lastName, t.middleName AS middleName, t.firstName AS firstName FROM Teacher t WHERE t.id IN :teacherIds")
    List<FullNameProjection> findFullNamesByTeacherIds(@Param("teacherIds") List<UUID> teacherIds);
}