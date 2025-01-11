package com.example.UniAssist.service;

import com.example.UniAssist.exception.ScheduleNotFound;
import com.example.UniAssist.model.entity.Subject;
import com.example.UniAssist.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class SubjectService {

    private SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> getStudentSchedule(UUID groupId, LocalDate date) {
        List<Subject> subjects = subjectRepository.findSubjectsByGroupAndDate(groupId, date);
        if (subjects.isEmpty()) {
            throw new ScheduleNotFound("No subjects found");
        }
        return subjects;
    }

    public List<Subject> getTeacherSchedule(UUID teacherId, LocalDate date) {
        List<Subject> subjects = subjectRepository.findSubjectsByTeacherAndDate(teacherId, date);
        if (subjects.isEmpty()) {
            throw new ScheduleNotFound("No subjects found");
        }
        return subjects;
    }
}

