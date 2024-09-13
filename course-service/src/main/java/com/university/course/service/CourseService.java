package com.university.course.service;

import com.university.course.api.dto.CourseDTO;
import com.university.course.api.dto.CreateCourseRequest;
import com.university.course.api.dto.CreateTimetableRequest;
import com.university.course.api.dto.TimetableDTO;
import com.university.course.api.dto.TutorDTO;
import com.university.course.api.dto.UpdateCourseRequest;

import java.util.List;

public interface CourseService {
    CourseDTO createCourse(CreateCourseRequest request);
    CourseDTO getCourseById(String id);
    List<CourseDTO> getAllCourses();
    CourseDTO updateCourse(String id, UpdateCourseRequest request);
    void deleteCourse(String id);


    CourseDTO assignTutorToCourse(String courseId, String tutorId);
    CourseDTO removeTutorFromCourse(String courseId, String tutorId);
    List<TutorDTO> getTutorsByCourseId(String courseId);

  
    TimetableDTO addTimetableToCourse(String courseId, CreateTimetableRequest request);
    void removeTimetableFromCourse(String courseId, String timetableId);
    List<TimetableDTO> getTimetablesByCourseId(String courseId);
}