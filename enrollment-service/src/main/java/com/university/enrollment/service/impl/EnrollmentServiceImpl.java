package com.university.enrollment.service.impl;

import com.university.enrollment.api.dto.EnrollmentDTO;
import com.university.enrollment.api.dto.GradeDTO;
import com.university.enrollment.api.dto.CreateEnrollmentRequest;
import com.university.enrollment.api.dto.UpdateGradeRequest;
import com.university.enrollment.domain.model.Enrollment;
import com.university.enrollment.domain.model.Grade;
import com.university.enrollment.domain.repository.EnrollmentRepository;
import com.university.enrollment.domain.repository.GradeRepository;
import com.university.enrollment.service.EnrollmentService;
import com.university.enrollment.exception.EnrollmentNotFoundException;
import com.university.enrollment.exception.GradeNotFoundException;
import com.university.enrollment.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final GradeRepository gradeRepository;
    private static final int MAX_ENROLLMENTS_PER_SEMESTER = 6;


    @Autowired
    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, GradeRepository gradeRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.gradeRepository = gradeRepository;
    }

    @Override
    @Transactional
    public EnrollmentDTO createEnrollment(CreateEnrollmentRequest request) {
        validateCreateEnrollmentRequest(request);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(request.getStudentId());
        enrollment.setCourseId(request.getCourseId());
        enrollment.setCourseName(request.getCourseName());
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setStatus(Enrollment.EnrollmentStatus.ACTIVE);
        enrollment.setSemester(request.getSemester()); 

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return convertToDTO(savedEnrollment);
    }

    @Override
    public EnrollmentDTO getEnrollmentById(String id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));
        return convertToDTO(enrollment);
    }

    @Override
    public List<EnrollmentDTO> getAllEnrollments() {
        return enrollmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EnrollmentDTO updateEnrollmentStatus(String id, String status) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));

        try {
            enrollment.setStatus(Enrollment.EnrollmentStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid enrollment status: " + status);
        }

        Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);
        return convertToDTO(updatedEnrollment);
    }

    @Override
    @Transactional
    public void deleteEnrollment(String id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new EnrollmentNotFoundException(id);
        }
        enrollmentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public GradeDTO addGrade(String enrollmentId, UpdateGradeRequest request) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new EnrollmentNotFoundException(enrollmentId));

        validateGradeRequest(request);

        Grade grade = new Grade();
        grade.setEnrollment(enrollment);  // Set the enrollment reference instead of ID
        grade.setLetterGrade(request.getLetterGrade());
        grade.setNumericGrade(request.getNumericGrade());
        grade.setDateRecorded(LocalDate.now());

        Grade savedGrade = gradeRepository.save(grade);
        return convertToGradeDTO(savedGrade);
    }

    @Override
    @Transactional
    public GradeDTO updateGrade(String gradeId, UpdateGradeRequest request) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new GradeNotFoundException(gradeId));

        validateGradeRequest(request);

        grade.setLetterGrade(request.getLetterGrade());
        grade.setNumericGrade(request.getNumericGrade());

        Grade updatedGrade = gradeRepository.save(grade);
        return convertToGradeDTO(updatedGrade);
    }

    @Override
    public GradeDTO getGradeById(String id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new GradeNotFoundException(id));
        return convertToGradeDTO(grade);
    }

    @Override
    public List<GradeDTO> getGradesByEnrollmentId(String enrollmentId) {
        return gradeRepository.findByEnrollment_Id(enrollmentId).stream()
                .map(this::convertToGradeDTO)
                .collect(Collectors.toList());
    }

    private void validateCreateEnrollmentRequest(CreateEnrollmentRequest request) {
        if (request.getStudentId() == null || request.getStudentId().trim().isEmpty()) {
            throw new ValidationException("Student ID is required");
        }
        if (request.getCourseId() == null || request.getCourseId().trim().isEmpty()) {
            throw new ValidationException("Course ID is required");
        }
    }

    private void validateGradeRequest(UpdateGradeRequest request) {
        if (request.getLetterGrade() == null || request.getLetterGrade().trim().isEmpty()) {
            throw new ValidationException("Letter grade is required");
        }
        if (request.getNumericGrade() < 0 || request.getNumericGrade() > 4.0) {
            throw new ValidationException("Numeric grade must be between 0 and 4.0");
        }
    }

    @Override
    public List<EnrollmentDTO> getEnrollmentsByStudentId(String studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        return enrollments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

 private EnrollmentDTO convertToDTO(Enrollment enrollment) {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setId(enrollment.getId());
        dto.setStudentId(enrollment.getStudentId());
        dto.setCourseId(enrollment.getCourseId());
        dto.setCourseName(enrollment.getCourseName());
        dto.setEnrollmentDate(enrollment.getEnrollmentDate());
        dto.setStatus(enrollment.getStatus().name());
        dto.setSemester(enrollment.getSemester());
        
        // Get the most recent grade
        String currentGrade = enrollment.getGrades().stream()
            .max(Comparator.comparing(Grade::getDateRecorded))
            .map(Grade::getLetterGrade)
            .orElse(null);
        dto.setGrade(currentGrade);

        return dto;
    }



    private GradeDTO convertToGradeDTO(Grade grade) {
        GradeDTO dto = new GradeDTO();
        dto.setId(grade.getId());
        dto.setEnrollmentId(grade.getEnrollment().getId()); 
        dto.setLetterGrade(grade.getLetterGrade());
        dto.setNumericGrade(grade.getNumericGrade());
        return dto;
    }
}