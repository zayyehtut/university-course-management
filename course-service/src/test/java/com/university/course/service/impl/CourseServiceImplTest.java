package com.university.course.service.impl;
import com.university.common.exception.*;
import com.university.course.exception.ProfessorNotFoundException;

import com.university.course.api.dto.ProfessorDTO;
import com.university.course.api.dto.CreateProfessorRequest;
import com.university.course.api.dto.UpdateProfessorRequest;
import com.university.course.domain.model.Professor;
import com.university.course.domain.model.Course;
import com.university.course.domain.repository.ProfessorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProfessorServiceImplTest {

    @Mock
    private ProfessorRepository professorRepository;

    @InjectMocks
    private ProfessorServiceImpl professorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProfessor_ValidRequest_ReturnsProfessorDTO() {
        CreateProfessorRequest request = new CreateProfessorRequest("John Doe", "Computer Science");
        Professor savedProfessor = new Professor();
        savedProfessor.setId("prof1");
        savedProfessor.setName("John Doe");
        savedProfessor.setDepartment("Computer Science");

        when(professorRepository.save(any(Professor.class))).thenReturn(savedProfessor);

        ProfessorDTO result = professorService.createProfessor(request);

        assertNotNull(result);
        assertEquals("prof1", result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("Computer Science", result.getDepartment());

        verify(professorRepository).save(any(Professor.class));
    }

    @Test
    void createProfessor_InvalidRequest_ThrowsValidationException() {
        CreateProfessorRequest request = new CreateProfessorRequest("", "");

        assertThrows(ValidationException.class, () -> professorService.createProfessor(request));

        verify(professorRepository, never()).save(any(Professor.class));
    }

    @Test
    void getProfessorById_ExistingId_ReturnsProfessorDTO() {
        String id = "prof1";
        Professor professor = new Professor();
        professor.setId(id);
        professor.setName("John Doe");
        professor.setDepartment("Computer Science");
        Course course1 = new Course();
        course1.setId("course1");
        Course course2 = new Course();
        course2.setId("course2");
        professor.setCourses(new HashSet<>(Arrays.asList(course1, course2)));

        when(professorRepository.findById(id)).thenReturn(Optional.of(professor));

        ProfessorDTO result = professorService.getProfessorById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("Computer Science", result.getDepartment());
        assertEquals(2, result.getCourseIds().size());
        assertTrue(result.getCourseIds().contains("course1"));
        assertTrue(result.getCourseIds().contains("course2"));

        verify(professorRepository).findById(id);
    }

    @Test
    void getProfessorById_NonExistingId_ThrowsProfessorNotFoundException() {
        String id = "nonexistent";
        when(professorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ProfessorNotFoundException.class, () -> professorService.getProfessorById(id));

        verify(professorRepository).findById(id);
    }

    @Test
    void getAllProfessors_ReturnsListOfProfessorDTOs() {
        Professor professor1 = new Professor();
        professor1.setId("prof1");
        professor1.setName("John Doe");
        professor1.setDepartment("Computer Science");

        Professor professor2 = new Professor();
        professor2.setId("prof2");
        professor2.setName("Jane Smith");
        professor2.setDepartment("Mathematics");

        when(professorRepository.findAll()).thenReturn(Arrays.asList(professor1, professor2));

        List<ProfessorDTO> result = professorService.getAllProfessors();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("prof1", result.get(0).getId());
        assertEquals("prof2", result.get(1).getId());

        verify(professorRepository).findAll();
    }

    @Test
    void updateProfessor_ValidRequest_ReturnsUpdatedProfessorDTO() {
        String id = "prof1";
        UpdateProfessorRequest request = new UpdateProfessorRequest("John Updated", "Physics");
        
        Professor existingProfessor = new Professor();
        existingProfessor.setId(id);
        existingProfessor.setName("John Doe");
        existingProfessor.setDepartment("Computer Science");

        when(professorRepository.findById(id)).thenReturn(Optional.of(existingProfessor));
        when(professorRepository.save(any(Professor.class))).thenReturn(existingProfessor);

        ProfessorDTO result = professorService.updateProfessor(id, request);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("John Updated", result.getName());
        assertEquals("Physics", result.getDepartment());

        verify(professorRepository).findById(id);
        verify(professorRepository).save(any(Professor.class));
    }

    @Test
    void updateProfessor_NonExistingId_ThrowsProfessorNotFoundException() {
        String id = "nonexistent";
        UpdateProfessorRequest request = new UpdateProfessorRequest("John Updated", "Physics");
        
        when(professorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ProfessorNotFoundException.class, () -> professorService.updateProfessor(id, request));

        verify(professorRepository).findById(id);
        verify(professorRepository, never()).save(any(Professor.class));
    }

    @Test
    void updateProfessor_InvalidRequest_ThrowsValidationException() {
        String id = "prof1";
        UpdateProfessorRequest request = new UpdateProfessorRequest("", "");
    
        assertThrows(ValidationException.class, () -> professorService.updateProfessor(id, request));
    
        // Verify that findById was never called due to early validation
        verify(professorRepository, never()).findById(any());
        verify(professorRepository, never()).save(any(Professor.class));
    }
    

    @Test
    void deleteProfessor_ExistingId_DeletesProfessor() {
        String id = "prof1";
        when(professorRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> professorService.deleteProfessor(id));

        verify(professorRepository).existsById(id);
        verify(professorRepository).deleteById(id);
    }

    @Test
    void deleteProfessor_NonExistingId_ThrowsProfessorNotFoundException() {
        String id = "nonexistent";
        when(professorRepository.existsById(id)).thenReturn(false);

        assertThrows(ProfessorNotFoundException.class, () -> professorService.deleteProfessor(id));

        verify(professorRepository).existsById(id);
        verify(professorRepository, never()).deleteById(any());
    }
}