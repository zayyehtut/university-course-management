package com.university.enrollment.service.impl;

import com.university.enrollment.api.dto.CreateEnrollmentRequest;
import com.university.enrollment.api.dto.EnrollmentDTO;
import com.university.enrollment.api.dto.GradeDTO;
import com.university.enrollment.api.dto.UpdateGradeRequest;
import com.university.enrollment.domain.model.Enrollment;
import com.university.enrollment.domain.model.Grade;
import com.university.enrollment.domain.repository.EnrollmentRepository;
import com.university.enrollment.domain.repository.GradeRepository;
import com.university.enrollment.exception.EnrollmentNotFoundException;
import com.university.enrollment.exception.GradeNotFoundException;
import com.university.enrollment.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class EnrollmentServiceImplTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createEnrollment_ValidRequest_ReturnsEnrollmentDTO() {
        CreateEnrollmentRequest request = new CreateEnrollmentRequest("student1", "course1");
        Enrollment savedEnrollment = new Enrollment();
        savedEnrollment.setId("enrollment1");
        savedEnrollment.setStudentId("student1");
        savedEnrollment.setCourseId("course1");
        savedEnrollment.setEnrollmentDate(LocalDate.now());
        savedEnrollment.setStatus(Enrollment.EnrollmentStatus.ACTIVE);

        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(savedEnrollment);

        EnrollmentDTO result = enrollmentService.createEnrollment(request);

        assertNotNull(result);
        assertEquals("enrollment1", result.getId());
        assertEquals("student1", result.getStudentId());
        assertEquals("course1", result.getCourseId());
        assertEquals("ACTIVE", result.getStatus());

        verify(enrollmentRepository).save(any(Enrollment.class));
    }

    @Test
    void createEnrollment_InvalidRequest_ThrowsValidationException() {
        CreateEnrollmentRequest request = new CreateEnrollmentRequest("", "");

        assertThrows(ValidationException.class, () -> enrollmentService.createEnrollment(request));

        verify(enrollmentRepository, never()).save(any(Enrollment.class));
    }

    @Test
    void getEnrollmentById_ExistingId_ReturnsEnrollmentDTO() {
        String id = "enrollment1";
        Enrollment enrollment = new Enrollment();
        enrollment.setId(id);
        enrollment.setStudentId("student1");
        enrollment.setCourseId("course1");
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setStatus(Enrollment.EnrollmentStatus.ACTIVE);

        when(enrollmentRepository.findById(id)).thenReturn(Optional.of(enrollment));

        EnrollmentDTO result = enrollmentService.getEnrollmentById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("student1", result.getStudentId());
        assertEquals("course1", result.getCourseId());
        assertEquals("ACTIVE", result.getStatus());

        verify(enrollmentRepository).findById(id);
    }

    @Test
    void getEnrollmentById_NonExistingId_ThrowsEnrollmentNotFoundException() {
        String id = "nonexistent";
        when(enrollmentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EnrollmentNotFoundException.class, () -> enrollmentService.getEnrollmentById(id));

        verify(enrollmentRepository).findById(id);
    }

    @Test
    void getAllEnrollments_ReturnsListOfEnrollmentDTOs() {
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setId("enrollment1");
        enrollment1.setStudentId("student1");
        enrollment1.setCourseId("course1");
        enrollment1.setEnrollmentDate(LocalDate.now());
        enrollment1.setStatus(Enrollment.EnrollmentStatus.ACTIVE);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setId("enrollment2");
        enrollment2.setStudentId("student2");
        enrollment2.setCourseId("course2");
        enrollment2.setEnrollmentDate(LocalDate.now());
        enrollment2.setStatus(Enrollment.EnrollmentStatus.ACTIVE);

        when(enrollmentRepository.findAll()).thenReturn(Arrays.asList(enrollment1, enrollment2));

        List<EnrollmentDTO> result = enrollmentService.getAllEnrollments();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("enrollment1", result.get(0).getId());
        assertEquals("enrollment2", result.get(1).getId());

        verify(enrollmentRepository).findAll();
    }

    @Test
    void updateEnrollmentStatus_ValidRequest_ReturnsUpdatedEnrollmentDTO() {
        String id = "enrollment1";
        String newStatus = "COMPLETED";
        Enrollment enrollment = new Enrollment();
        enrollment.setId(id);
        enrollment.setStudentId("student1");
        enrollment.setCourseId("course1");
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setStatus(Enrollment.EnrollmentStatus.ACTIVE);

        when(enrollmentRepository.findById(id)).thenReturn(Optional.of(enrollment));
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);

        EnrollmentDTO result = enrollmentService.updateEnrollmentStatus(id, newStatus);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("COMPLETED", result.getStatus());

        verify(enrollmentRepository).findById(id);
        verify(enrollmentRepository).save(any(Enrollment.class));
    }

    @Test
    void updateEnrollmentStatus_InvalidStatus_ThrowsValidationException() {
        String id = "enrollment1";
        String invalidStatus = "INVALID_STATUS";
        Enrollment enrollment = new Enrollment();
        enrollment.setId(id);

        when(enrollmentRepository.findById(id)).thenReturn(Optional.of(enrollment));

        assertThrows(ValidationException.class, () -> enrollmentService.updateEnrollmentStatus(id, invalidStatus));

        verify(enrollmentRepository).findById(id);
        verify(enrollmentRepository, never()).save(any(Enrollment.class));
    }

    @Test
    void deleteEnrollment_ExistingId_DeletesEnrollment() {
        String id = "enrollment1";
        when(enrollmentRepository.existsById(id)).thenReturn(true);

        enrollmentService.deleteEnrollment(id);

        verify(enrollmentRepository).existsById(id);
        verify(enrollmentRepository).deleteById(id);
    }

    @Test
    void deleteEnrollment_NonExistingId_ThrowsEnrollmentNotFoundException() {
        String id = "nonexistent";
        when(enrollmentRepository.existsById(id)).thenReturn(false);

        assertThrows(EnrollmentNotFoundException.class, () -> enrollmentService.deleteEnrollment(id));

        verify(enrollmentRepository).existsById(id);
        verify(enrollmentRepository, never()).deleteById(anyString());
    }

    @Test
    void addGrade_ValidRequest_ReturnsGradeDTO() {
        String enrollmentId = "enrollment1";
        UpdateGradeRequest request = new UpdateGradeRequest("A", 4.0f);
        Enrollment enrollment = new Enrollment();
        enrollment.setId(enrollmentId);
        Grade savedGrade = new Grade();
        savedGrade.setId("grade1");
        savedGrade.setEnrollment(enrollment);
        savedGrade.setLetterGrade("A");
        savedGrade.setNumericGrade(4.0f);

        when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.of(enrollment));
        when(gradeRepository.save(any(Grade.class))).thenReturn(savedGrade);

        GradeDTO result = enrollmentService.addGrade(enrollmentId, request);

        assertNotNull(result);
        assertEquals("grade1", result.getId());
        assertEquals(enrollmentId, result.getEnrollmentId());
        assertEquals("A", result.getLetterGrade());
        assertEquals(4.0f, result.getNumericGrade());

        verify(enrollmentRepository).findById(enrollmentId);
        verify(gradeRepository).save(any(Grade.class));
    }

    @Test
    void addGrade_InvalidRequest_ThrowsValidationException() {
        String enrollmentId = "enrollment1";
        UpdateGradeRequest request = new UpdateGradeRequest("", -1.0f);
        Enrollment enrollment = new Enrollment();
        enrollment.setId(enrollmentId);

        when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.of(enrollment));

        assertThrows(ValidationException.class, () -> enrollmentService.addGrade(enrollmentId, request));

        verify(enrollmentRepository).findById(enrollmentId);
        verify(gradeRepository, never()).save(any(Grade.class));
    }

   @Test
void updateGrade_ValidRequest_ReturnsUpdatedGradeDTO() {
    String gradeId = "grade1";
    UpdateGradeRequest request = new UpdateGradeRequest("B", 3.0f);
    
    // Create an Enrollment object
    Enrollment enrollment = new Enrollment();
    enrollment.setId("enrollment1");

    // Create the existing Grade with a reference to the Enrollment
    Grade existingGrade = new Grade();
    existingGrade.setId(gradeId);
    existingGrade.setEnrollment(enrollment);
    existingGrade.setLetterGrade("A");
    existingGrade.setNumericGrade(4.0f);

    when(gradeRepository.findById(gradeId)).thenReturn(Optional.of(existingGrade));
    when(gradeRepository.save(any(Grade.class))).thenReturn(existingGrade);

    GradeDTO result = enrollmentService.updateGrade(gradeId, request);

    assertNotNull(result);
    assertEquals(gradeId, result.getId());
    assertEquals("enrollment1", result.getEnrollmentId());
    assertEquals("B", result.getLetterGrade());
    assertEquals(3.0f, result.getNumericGrade());

    verify(gradeRepository).findById(gradeId);
    verify(gradeRepository).save(any(Grade.class));
}

    @Test
    void updateGrade_NonExistingId_ThrowsGradeNotFoundException() {
        String gradeId = "nonexistent";
        UpdateGradeRequest request = new UpdateGradeRequest("B", 3.0f);

        when(gradeRepository.findById(gradeId)).thenReturn(Optional.empty());

        assertThrows(GradeNotFoundException.class, () -> enrollmentService.updateGrade(gradeId, request));

        verify(gradeRepository).findById(gradeId);
        verify(gradeRepository, never()).save(any(Grade.class));
    }

    @Test
    void getGradeById_ExistingId_ReturnsGradeDTO() {
        String gradeId = "grade1";
        Enrollment enrollment = new Enrollment();
        enrollment.setId("enrollment1");

        Grade grade = new Grade();
        grade.setId(gradeId);
        grade.setEnrollment(enrollment);
        grade.setLetterGrade("A");
        grade.setNumericGrade(4.0f);

        when(gradeRepository.findById(gradeId)).thenReturn(Optional.of(grade));

        GradeDTO result = enrollmentService.getGradeById(gradeId);

        assertNotNull(result);
        assertEquals(gradeId, result.getId());
        assertEquals("enrollment1", result.getEnrollmentId());
        assertEquals("A", result.getLetterGrade());
        assertEquals(4.0f, result.getNumericGrade());

        verify(gradeRepository).findById(gradeId);
    }

    @Test
    void getGradeById_NonExistingId_ThrowsGradeNotFoundException() {
        String gradeId = "nonexistent";
        when(gradeRepository.findById(gradeId)).thenReturn(Optional.empty());

        assertThrows(GradeNotFoundException.class, () -> enrollmentService.getGradeById(gradeId));

        verify(gradeRepository).findById(gradeId);
    }

    @Test
void getGradesByEnrollmentId_ReturnsListOfGradeDTOs() {
    String enrollmentId = "enrollment1";
    
    Enrollment enrollment = new Enrollment();
    enrollment.setId(enrollmentId);

    Grade grade1 = new Grade();
    grade1.setId("grade1");
    grade1.setEnrollment(enrollment);
    grade1.setLetterGrade("A");
    grade1.setNumericGrade(4.0f);

    Grade grade2 = new Grade();
    grade2.setId("grade2");
    grade2.setEnrollment(enrollment);
    grade2.setLetterGrade("B");
    grade2.setNumericGrade(3.0f);

    when(gradeRepository.findByEnrollment_Id(enrollmentId)).thenReturn(Arrays.asList(grade1, grade2));

    List<GradeDTO> result = enrollmentService.getGradesByEnrollmentId(enrollmentId);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("grade1", result.get(0).getId());
    assertEquals("grade2", result.get(1).getId());
    
    // Check that the enrollmentId is correctly set in the DTOs
    assertEquals(enrollmentId, result.get(0).getEnrollmentId());
    assertEquals(enrollmentId, result.get(1).getEnrollmentId());

    assertEquals("A", result.get(0).getLetterGrade());
    assertEquals(4.0f, result.get(0).getNumericGrade());
    assertEquals("B", result.get(1).getLetterGrade());
    assertEquals(3.0f, result.get(1).getNumericGrade());

    verify(gradeRepository).findByEnrollment_Id(enrollmentId);
}

}