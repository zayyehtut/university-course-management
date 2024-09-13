package com.university.course.api.controllers;

import com.university.course.api.dto.CourseDTO;
import com.university.course.api.dto.CreateCourseRequest;
import com.university.course.api.dto.CreateTimetableRequest;
import com.university.course.api.dto.TimetableDTO;
import com.university.course.api.dto.TutorDTO;
import com.university.course.api.dto.UpdateCourseRequest;
import com.university.course.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CreateCourseRequest request) {
        CourseDTO createdCourse = courseService.createCourse(request);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable String id) {
        CourseDTO course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<CourseDTO> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable String id, @Valid @RequestBody UpdateCourseRequest request) {
        CourseDTO updatedCourse = courseService.updateCourse(id, request);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

     @GetMapping("/{courseId}/tutors")
    public ResponseEntity<List<TutorDTO>> getTutorsForCourse(@PathVariable String courseId) {
        List<TutorDTO> tutors = courseService.getTutorsByCourseId(courseId);
        return ResponseEntity.ok(tutors);
    }

    @PostMapping("/{courseId}/tutors/{tutorId}")
    public ResponseEntity<CourseDTO> assignTutorToCourse(@PathVariable String courseId, @PathVariable String tutorId) {
        CourseDTO updatedCourse = courseService.assignTutorToCourse(courseId, tutorId);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{courseId}/tutors/{tutorId}")
    public ResponseEntity<CourseDTO> removeTutorFromCourse(@PathVariable String courseId, @PathVariable String tutorId) {
        CourseDTO updatedCourse = courseService.removeTutorFromCourse(courseId, tutorId);
        return ResponseEntity.ok(updatedCourse);
    }

    @PostMapping("/{courseId}/timetables")
    public ResponseEntity<TimetableDTO> addTimetableToCourse(@PathVariable String courseId, @Valid @RequestBody CreateTimetableRequest request) {
        request.setCourseId(courseId);
        TimetableDTO createdTimetable = courseService.addTimetableToCourse(courseId, request);
        return new ResponseEntity<>(createdTimetable, HttpStatus.CREATED);
    }

    @DeleteMapping("/{courseId}/timetables/{timetableId}")
    public ResponseEntity<Void> removeTimetableFromCourse(@PathVariable String courseId, @PathVariable String timetableId) {
        courseService.removeTimetableFromCourse(courseId, timetableId);
        return ResponseEntity.noContent().build();
    }
}