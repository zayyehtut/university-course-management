package com.university.course.service;

import com.university.course.api.dto.CourseDTO;
import com.university.course.api.dto.CreateCourseRequest;
import com.university.course.api.dto.UpdateCourseRequest;

import java.util.List;

public interface CourseService {
    CourseDTO createCourse(CreateCourseRequest request);
    CourseDTO getCourseById(String id);
    List<CourseDTO> getAllCourses();
    CourseDTO updateCourse(String id, UpdateCourseRequest request);
    void deleteCourse(String id);
}