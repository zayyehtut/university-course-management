// File: course-service/src/main/java/com/university/course/api/controllers/TutorController.java
package com.university.course.api.controllers;

import com.university.course.api.dto.TutorDTO;
import com.university.course.api.dto.CreateTutorRequest;
import com.university.course.api.dto.UpdateTutorRequest;
import com.university.course.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutors")
public class TutorController {

    private final TutorService tutorService;

    @Autowired
    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    @PostMapping
    public ResponseEntity<TutorDTO> createTutor(@RequestBody CreateTutorRequest request) {
        TutorDTO createdTutor = tutorService.createTutor(request);
        return new ResponseEntity<>(createdTutor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorDTO> getTutor(@PathVariable String id) {
        TutorDTO tutor = tutorService.getTutorById(id);
        return ResponseEntity.ok(tutor);
    }

    @GetMapping
    public ResponseEntity<List<TutorDTO>> getAllTutors() {
        List<TutorDTO> tutors = tutorService.getAllTutors();
        return ResponseEntity.ok(tutors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TutorDTO> updateTutor(@PathVariable String id, @RequestBody UpdateTutorRequest request) {
        TutorDTO updatedTutor = tutorService.updateTutor(id, request);
        return ResponseEntity.ok(updatedTutor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTutor(@PathVariable String id) {
        tutorService.deleteTutor(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tutorId}/courses/{courseId}")
    public ResponseEntity<Void> assignTutorToCourse(@PathVariable String tutorId, @PathVariable String courseId) {
        tutorService.assignTutorToCourse(tutorId, courseId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{tutorId}/courses/{courseId}")
    public ResponseEntity<Void> removeTutorFromCourse(@PathVariable String tutorId, @PathVariable String courseId) {
        tutorService.removeTutorFromCourse(tutorId, courseId);
        return ResponseEntity.ok().build();
    }
}