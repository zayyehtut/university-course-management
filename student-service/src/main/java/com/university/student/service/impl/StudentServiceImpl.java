package com.university.student.service.impl;

import com.university.common.exception.*;
import com.university.common.util.ValidationUtils;

import com.university.student.service.StudentService;
import com.university.student.api.dto.*;
import com.university.common.dto.EnrollmentDTO;
import com.university.student.domain.model.Student;
import com.university.student.domain.model.StudentProfile;
import com.university.student.domain.repository.StudentRepository;
import com.university.student.exception.StudentNotFoundException;

import com.university.student.exception.DuplicateEmailException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final RestTemplate restTemplate;


    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, RestTemplate restTemplate) {
        this.studentRepository = studentRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    @Transactional
    public StudentDTO createStudent(CreateStudentRequest request) {
        validateCreateStudentRequest(request);
        
        if (studentRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateEmailException(request.getEmail());
        }

        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setType(Student.StudentType.valueOf(request.getType()));

        StudentProfile profile = new StudentProfile();
        profile.setAddress(request.getAddress());
        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setDateOfBirth(request.getDateOfBirth());

        student.setProfile(profile);

        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }

    @Override
    public StudentDTO getStudentById(String id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        return convertToDTO(student);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudentDTO updateStudent(String id, UpdateStudentRequest request) {
        validateUpdateStudentRequest(request);

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        if (request.getEmail() != null && !request.getEmail().equals(student.getEmail())) {
            if (studentRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new DuplicateEmailException(request.getEmail());
            }
            student.setEmail(request.getEmail());
        }

        if (request.getName() != null) {
            student.setName(request.getName());
        }
        if (request.getType() != null) {
            student.setType(Student.StudentType.valueOf(request.getType()));
        }

        StudentProfile profile = student.getProfile();
        if (request.getAddress() != null) {
            profile.setAddress(request.getAddress());
        }
        if (request.getPhoneNumber() != null) {
            profile.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getDateOfBirth() != null) {
            profile.setDateOfBirth(request.getDateOfBirth());
        }

        Student updatedStudent = studentRepository.save(student);
        return convertToDTO(updatedStudent);
    }

    @Override
    @Transactional
    public void deleteStudent(String id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }

    private void validateCreateStudentRequest(CreateStudentRequest request) {
        ValidationUtils.validateNonEmptyString(request.getName(), "Name");
        ValidationUtils.validateEmail(request.getEmail());
        ValidationUtils.validateType(request.getType(), new String[]{"UNDERGRADUATE", "POSTGRADUATE"}, "Type");
        ValidationUtils.validateNonEmptyString(request.getAddress(), "Address");
        ValidationUtils.validatePhoneNumber(request.getPhoneNumber());
        ValidationUtils.validateDateInPast(request.getDateOfBirth(), "Date of birth");
    }

     private void validateUpdateStudentRequest(UpdateStudentRequest request) {
        List<String> errors = new ArrayList<>();

        if (request.getName() != null && request.getName().trim().isEmpty()) {
            ValidationUtils.validateNonEmptyString(request.getName(), "Name");
        }
        if (request.getEmail() != null && !request.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            ValidationUtils.validateEmail(request.getEmail());
        }
        if (request.getType() != null && (!request.getType().equals("UNDERGRADUATE") && !request.getType().equals("POSTGRADUATE"))) {
            ValidationUtils.validateType(request.getType(), new String[]{"UNDERGRADUATE", "POSTGRADUATE"}, "Type");
        }
        if (request.getAddress() != null && request.getAddress().trim().isEmpty()) {
            ValidationUtils.validateNonEmptyString(request.getAddress(), "Address");
        }
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().matches("\\d{10}")) {
            ValidationUtils.validatePhoneNumber(request.getPhoneNumber());
        }
        if (request.getDateOfBirth() != null && request.getDateOfBirth().isAfter(LocalDate.now())) {
            ValidationUtils.validateDateInPast(request.getDateOfBirth(), "Date of birth");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(String.join(", ", errors));
        }
    }

    @Override
public AcademicRecordDTO getAcademicRecord(String studentId) {
    Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new StudentNotFoundException(studentId));
    
    List<AcademicRecordDTO.EnrollmentRecordDTO> enrollments = fetchEnrollmentRecords(studentId);
    double gpa = calculateGPA(enrollments);

    return new AcademicRecordDTO(student.getId(), student.getName(), enrollments, gpa);
}

    

private List<AcademicRecordDTO.EnrollmentRecordDTO> fetchEnrollmentRecords(String studentId) {
    String enrollmentServiceUrl = "http://localhost:8082/api/enrollments/student/" + studentId;
    try {
        EnrollmentDTO[] enrollments = restTemplate.getForObject(enrollmentServiceUrl, EnrollmentDTO[].class);
        if (enrollments != null) {
            return Arrays.stream(enrollments)
                    .map(this::convertToEnrollmentRecordDTO)
                    .collect(Collectors.toList());
        }
    } catch (Exception e) {
        // Log the error
    }
    return Collections.emptyList();
}

private AcademicRecordDTO.EnrollmentRecordDTO convertToEnrollmentRecordDTO(EnrollmentDTO enrollmentDTO) {
    return new AcademicRecordDTO.EnrollmentRecordDTO(
        enrollmentDTO.getCourseId(),
        enrollmentDTO.getCourseName(),
        enrollmentDTO.getGrade(),
        enrollmentDTO.getSemester()
    );
}


    private double calculateGPA(List<AcademicRecordDTO.EnrollmentRecordDTO> enrollments) {
        if (enrollments.isEmpty()) {
            return 0.0;
        }
        double totalGradePoints = enrollments.stream()
                .mapToDouble(e -> convertGradeToPoints(e.getGrade()))
                .sum();
        return totalGradePoints / enrollments.size();
    }

    private double convertGradeToPoints(String grade) {
        switch (grade.toUpperCase()) {
            case "A": return 4.0;
            case "B": return 3.0;
            case "C": return 2.0;
            case "D": return 1.0;
            default: return 0.0;
        }

    }

    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setType(student.getType().name());
        dto.setAddress(student.getProfile().getAddress());
        dto.setPhoneNumber(student.getProfile().getPhoneNumber());
        dto.setDateOfBirth(student.getProfile().getDateOfBirth());
        return dto;
    }
}