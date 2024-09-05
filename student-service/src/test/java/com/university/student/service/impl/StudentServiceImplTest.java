package com.university.student.service.impl;

import com.university.student.api.dto.CreateStudentRequest;
import com.university.student.api.dto.StudentDTO;
import com.university.student.api.dto.UpdateStudentRequest;
import com.university.student.domain.model.Student;
import com.university.student.domain.model.StudentProfile;
import com.university.student.domain.repository.StudentRepository;
import com.university.student.exception.DuplicateEmailException;
import com.university.student.exception.StudentNotFoundException;
import com.university.student.exception.ValidationException;
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

class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createStudent_ValidRequest_ReturnsStudentDTO() {
        CreateStudentRequest request = new CreateStudentRequest("John Doe", "john@example.com", "UNDERGRADUATE", "123 Main St", "1234567890", LocalDate.of(2000, 1, 1));
        Student savedStudent = new Student("John Doe", "john@example.com", Student.StudentType.UNDERGRADUATE);
        savedStudent.setId("1");
        savedStudent.setProfile(new StudentProfile("123 Main St", "1234567890", LocalDate.of(2000, 1, 1)));

        when(studentRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        StudentDTO result = studentService.createStudent(request);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("UNDERGRADUATE", result.getType());
        assertEquals("123 Main St", result.getAddress());
        assertEquals("1234567890", result.getPhoneNumber());
        assertEquals(LocalDate.of(2000, 1, 1), result.getDateOfBirth());

        verify(studentRepository).findByEmail(request.getEmail());
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void createStudent_DuplicateEmail_ThrowsDuplicateEmailException() {
        CreateStudentRequest request = new CreateStudentRequest("John Doe", "john@example.com", "UNDERGRADUATE", "123 Main St", "1234567890", LocalDate.of(2000, 1, 1));
        
        when(studentRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new Student()));

        assertThrows(DuplicateEmailException.class, () -> studentService.createStudent(request));

        verify(studentRepository).findByEmail(request.getEmail());
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void createStudent_InvalidRequest_ThrowsValidationException() {
        CreateStudentRequest request = new CreateStudentRequest("", "invalid-email", "INVALID_TYPE", "", "123", LocalDate.now().plusDays(1));

        assertThrows(ValidationException.class, () -> studentService.createStudent(request));

        verify(studentRepository, never()).findByEmail(anyString());
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void getStudentById_ExistingId_ReturnsStudentDTO() {
        String id = "1";
        Student student = new Student("John Doe", "john@example.com", Student.StudentType.UNDERGRADUATE);
        student.setId(id);
        student.setProfile(new StudentProfile("123 Main St", "1234567890", LocalDate.of(2000, 1, 1)));

        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        StudentDTO result = studentService.getStudentById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("UNDERGRADUATE", result.getType());

        verify(studentRepository).findById(id);
    }

    @Test
    void getStudentById_NonExistingId_ThrowsStudentNotFoundException() {
        String id = "1";
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.getStudentById(id));

        verify(studentRepository).findById(id);
    }

    @Test
    void getAllStudents_ReturnsListOfStudentDTOs() {
        Student student1 = new Student("John Doe", "john@example.com", Student.StudentType.UNDERGRADUATE);
        student1.setId("1");
        student1.setProfile(new StudentProfile("123 Main St", "1234567890", LocalDate.of(2000, 1, 1)));

        Student student2 = new Student("Jane Doe", "jane@example.com", Student.StudentType.POSTGRADUATE);
        student2.setId("2");
        student2.setProfile(new StudentProfile("456 Elm St", "0987654321", LocalDate.of(1995, 5, 5)));

        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));

        List<StudentDTO> result = studentService.getAllStudents();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("2", result.get(1).getId());

        verify(studentRepository).findAll();
    }

    @Test
    void updateStudent_ValidRequest_ReturnsUpdatedStudentDTO() {
        String id = "1";
        UpdateStudentRequest request = new UpdateStudentRequest("John Updated", "john.updated@example.com", "POSTGRADUATE", "456 New St", "9876543210", LocalDate.of(1999, 12, 31));
        
        Student existingStudent = new Student("John Doe", "john@example.com", Student.StudentType.UNDERGRADUATE);
        existingStudent.setId(id);
        existingStudent.setProfile(new StudentProfile("123 Main St", "1234567890", LocalDate.of(2000, 1, 1)));

        when(studentRepository.findById(id)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(studentRepository.save(any(Student.class))).thenReturn(existingStudent);

        StudentDTO result = studentService.updateStudent(id, request);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("John Updated", result.getName());
        assertEquals("john.updated@example.com", result.getEmail());
        assertEquals("POSTGRADUATE", result.getType());
        assertEquals("456 New St", result.getAddress());
        assertEquals("9876543210", result.getPhoneNumber());
        assertEquals(LocalDate.of(1999, 12, 31), result.getDateOfBirth());

        verify(studentRepository).findById(id);
        verify(studentRepository).findByEmail(request.getEmail());
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void updateStudent_NonExistingId_ThrowsStudentNotFoundException() {
        String id = "1";
        UpdateStudentRequest request = new UpdateStudentRequest("John Updated", "john.updated@example.com", "POSTGRADUATE", "456 New St", "9876543210", LocalDate.of(1999, 12, 31));
        
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.updateStudent(id, request));

        verify(studentRepository).findById(id);
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void updateStudent_DuplicateEmail_ThrowsDuplicateEmailException() {
        String id = "1";
        UpdateStudentRequest request = new UpdateStudentRequest("John Updated", "john.updated@example.com", "POSTGRADUATE", "456 New St", "9876543210", LocalDate.of(1999, 12, 31));
        
        Student existingStudent = new Student("John Doe", "john@example.com", Student.StudentType.UNDERGRADUATE);
        existingStudent.setId(id);
        existingStudent.setProfile(new StudentProfile("123 Main St", "1234567890", LocalDate.of(2000, 1, 1)));

        when(studentRepository.findById(id)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new Student()));

        assertThrows(DuplicateEmailException.class, () -> studentService.updateStudent(id, request));

        verify(studentRepository).findById(id);
        verify(studentRepository).findByEmail(request.getEmail());
        verify(studentRepository, never()).save(any(Student.class));
    }

 @Test
void updateStudent_InvalidRequest_ThrowsValidationException() {
    String id = "1";
    UpdateStudentRequest request = new UpdateStudentRequest("", "invalid-email", "INVALID_TYPE", "", "123", LocalDate.now().plusDays(1));

    assertThrows(ValidationException.class, () -> studentService.updateStudent(id, request));

    verifyNoInteractions(studentRepository);
}

    @Test
    void deleteStudent_ExistingId_DeletesStudent() {
        String id = "1";
        when(studentRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> studentService.deleteStudent(id));

        verify(studentRepository).existsById(id);
        verify(studentRepository).deleteById(id);
    }

    @Test
    void deleteStudent_NonExistingId_ThrowsStudentNotFoundException() {
        String id = "1";
        when(studentRepository.existsById(id)).thenReturn(false);

        assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudent(id));

        verify(studentRepository).existsById(id);
        verify(studentRepository, never()).deleteById(any());
    }
}