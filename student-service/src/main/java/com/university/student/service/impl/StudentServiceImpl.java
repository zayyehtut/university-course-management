package com.university.student.service.impl;

import com.university.student.service.StudentService;
import com.university.student.api.dto.StudentDTO;
import com.university.student.api.dto.CreateStudentRequest;
import com.university.student.api.dto.UpdateStudentRequest;
import com.university.student.domain.model.Student;
import com.university.student.domain.model.StudentProfile;
import com.university.student.domain.repository.StudentRepository;
import com.university.student.exception.StudentNotFoundException;
import com.university.student.exception.ValidationException;
import com.university.student.exception.DuplicateEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new ValidationException("Name is required");
        }
        if (request.getEmail() == null || !request.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new ValidationException("Valid email is required");
        }
        if (request.getType() == null || (!request.getType().equals("UNDERGRADUATE") && !request.getType().equals("POSTGRADUATE"))) {
            throw new ValidationException("Type must be either UNDERGRADUATE or POSTGRADUATE");
        }
        if (request.getAddress() == null || request.getAddress().trim().isEmpty()) {
            throw new ValidationException("Address is required");
        }
        if (request.getPhoneNumber() == null || !request.getPhoneNumber().matches("\\d{10}")) {
            throw new ValidationException("Phone number must be 10 digits");
        }
        if (request.getDateOfBirth() == null || request.getDateOfBirth().isAfter(LocalDate.now())) {
            throw new ValidationException("Valid date of birth is required and must be in the past");
        }
    }

     private void validateUpdateStudentRequest(UpdateStudentRequest request) {
        List<String> errors = new ArrayList<>();

        if (request.getName() != null && request.getName().trim().isEmpty()) {
            errors.add("Name cannot be empty");
        }
        if (request.getEmail() != null && !request.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errors.add("Valid email is required");
        }
        if (request.getType() != null && (!request.getType().equals("UNDERGRADUATE") && !request.getType().equals("POSTGRADUATE"))) {
            errors.add("Type must be either UNDERGRADUATE or POSTGRADUATE");
        }
        if (request.getAddress() != null && request.getAddress().trim().isEmpty()) {
            errors.add("Address cannot be empty");
        }
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().matches("\\d{10}")) {
            errors.add("Phone number must be 10 digits");
        }
        if (request.getDateOfBirth() != null && request.getDateOfBirth().isAfter(LocalDate.now())) {
            errors.add("Date of birth must be in the past");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(String.join(", ", errors));
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