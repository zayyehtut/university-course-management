package com.university.enrollment.api.controllers;

import com.university.common.dto.EnrollmentDTO;
import com.university.enrollment.api.dto.GradeDTO;
import com.university.enrollment.api.dto.CreateEnrollmentRequest;
import com.university.enrollment.api.dto.UpdateGradeRequest;
import com.university.enrollment.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<EnrollmentDTO> createEnrollment(@RequestBody CreateEnrollmentRequest request) {
        EnrollmentDTO createdEnrollment = enrollmentService.createEnrollment(request);
        return new ResponseEntity<>(createdEnrollment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> getEnrollment(@PathVariable String id) {
        EnrollmentDTO enrollment = enrollmentService.getEnrollmentById(id);
        return ResponseEntity.ok(enrollment);
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentDTO>> getAllEnrollments() {
        List<EnrollmentDTO> enrollments = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(enrollments);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<EnrollmentDTO> updateEnrollmentStatus(@PathVariable String id, @RequestParam String status) {
        EnrollmentDTO updatedEnrollment = enrollmentService.updateEnrollmentStatus(id, status);
        return ResponseEntity.ok(updatedEnrollment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable String id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{enrollmentId}/grades")
    public ResponseEntity<GradeDTO> addGrade(@PathVariable String enrollmentId, @RequestBody UpdateGradeRequest request) {
        GradeDTO addedGrade = enrollmentService.addGrade(enrollmentId, request);
        return new ResponseEntity<>(addedGrade, HttpStatus.CREATED);
    }

    @PutMapping("/grades/{gradeId}")
    public ResponseEntity<GradeDTO> updateGrade(@PathVariable String gradeId, @RequestBody UpdateGradeRequest request) {
        GradeDTO updatedGrade = enrollmentService.updateGrade(gradeId, request);
        return ResponseEntity.ok(updatedGrade);
    }

    @GetMapping("/grades/{gradeId}")
    public ResponseEntity<GradeDTO> getGrade(@PathVariable String gradeId) {
        GradeDTO grade = enrollmentService.getGradeById(gradeId);
        return ResponseEntity.ok(grade);
    }

    @GetMapping("/{enrollmentId}/grades")
    public ResponseEntity<List<GradeDTO>> getGradesByEnrollment(@PathVariable String enrollmentId) {
        List<GradeDTO> grades = enrollmentService.getGradesByEnrollmentId(enrollmentId);
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsByStudentId(@PathVariable String studentId) {
        List<EnrollmentDTO> enrollments = enrollmentService.getEnrollmentsByStudentId(studentId);
        return ResponseEntity.ok(enrollments);
    }


}
