package com.example.UniAssist.service;

import com.example.UniAssist.exception.ScheduleNotFound;
import com.example.UniAssist.model.entity.Class;
import com.example.UniAssist.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ClassService {

    private ClassRepository classRepository;

    @Autowired
    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public List<Class> getStudentSchedule(UUID groupId, LocalDate date) {
        List<Class> classes = classRepository.findClassesByGroupAndDate(groupId, date);
        if (classes.isEmpty()) {
            throw new ScheduleNotFound("No classes found");
        }
        return classes;
    }

    public List<Class> getTeacherSchedule(UUID teacherId, LocalDate date) {
        List<Class> classes = classRepository.findClassesByTeacherAndDate(teacherId, date);
        if (classes.isEmpty()) {
            throw new ScheduleNotFound("No classes found");
        }
        return classes;
    }
}

