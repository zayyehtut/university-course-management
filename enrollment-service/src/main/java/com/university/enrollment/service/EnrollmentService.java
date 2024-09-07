package com.university.enrollment.service;

import com.university.enrollment.api.dto.*;
import java.util.List;

public interface EnrollmentService {
    EnrollmentDTO createEnrollment(CreateEnrollmentRequest request);
    EnrollmentDTO getEnrollmentById(String id);
    List<EnrollmentDTO> getAllEnrollments();
    EnrollmentDTO updateEnrollmentStatus(String id, String status);
    void deleteEnrollment(String id);
    GradeDTO addGrade(String enrollmentId, UpdateGradeRequest request);
    GradeDTO updateGrade(String gradeId, UpdateGradeRequest request);
    GradeDTO getGradeById(String id);
    List<GradeDTO> getGradesByEnrollmentId(String enrollmentId);
    List<EnrollmentDTO> getEnrollmentsByStudentId(String studentId);
}
