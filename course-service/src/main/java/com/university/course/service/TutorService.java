package com.university.course.service;

import com.university.course.api.dto.TutorDTO;
import com.university.course.api.dto.CreateTutorRequest;
import com.university.course.api.dto.UpdateTutorRequest;

import java.util.List;

public interface TutorService {
    TutorDTO createTutor(CreateTutorRequest request);
    TutorDTO getTutorById(String id);
    List<TutorDTO> getAllTutors();
    TutorDTO updateTutor(String id, UpdateTutorRequest request);
    void deleteTutor(String id);
    void assignTutorToCourse(String tutorId, String courseId);
    void removeTutorFromCourse(String tutorId, String courseId);
}