package com.university.student.service.impl;
import com.university.student.util.StudentValidationUtils;
import com.university.student.util.AcademicRecordUtils;

import com.university.student.service.StudentService;
import com.university.student.api.dto.*;
import com.university.student.domain.model.Student;
import com.university.student.domain.model.StudentProfile;
import com.university.student.domain.repository.StudentRepository;
import com.university.student.exception.StudentNotFoundException;
import com.university.student.exception.DuplicateEmailException;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, RestTemplate restTemplate, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.restTemplate = restTemplate;
        this.modelMapper = modelMapper;
        configureModelMapper();

    }

    private void configureModelMapper() {
        modelMapper.createTypeMap(CreateStudentRequest.class, Student.class)
            .addMappings(mapper -> {
                mapper.skip(Student::setId);
                mapper.map(src -> src.getType() != null ? Student.StudentType.valueOf(src.getType().toUpperCase()) : null, Student::setType);
            });

        modelMapper.createTypeMap(Student.class, StudentDTO.class)
            .addMappings(mapper -> {
                mapper.map(src -> src.getProfile().getAddress(), StudentDTO::setAddress);
                mapper.map(src -> src.getProfile().getPhoneNumber(), StudentDTO::setPhoneNumber);
                mapper.map(src -> src.getProfile().getDateOfBirth(), StudentDTO::setDateOfBirth);
            });
    }

    @Override
    @Transactional
    public StudentDTO createStudent(CreateStudentRequest request) {
        StudentValidationUtils.validateCreateStudentRequest(request);
        
        if (studentRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateEmailException(request.getEmail());
        }

        Student student = modelMapper.map(request, Student.class);
        StudentProfile profile = modelMapper.map(request, StudentProfile.class);

        student.setProfile(profile);
        profile.setStudent(student);

        Student savedStudent = studentRepository.save(student);
        return modelMapper.map(savedStudent, StudentDTO.class);
    }

    @Override
    public StudentDTO getStudentById(String id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        return modelMapper.map(student, StudentDTO.class);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudentDTO updateStudent(String id, UpdateStudentRequest request) {
    StudentValidationUtils.validateUpdateStudentRequest(request);

    Student student = studentRepository.findById(id)
            .orElseThrow(() -> new StudentNotFoundException(id));

    if (request.getEmail() != null && !request.getEmail().equals(student.getEmail())) {
        if (studentRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateEmailException(request.getEmail());
        }
    }

    modelMapper.map(request, student);
    
    if (student.getProfile() == null) {
        student.setProfile(new StudentProfile());
    }
    modelMapper.map(request, student.getProfile());

    Student updatedStudent = studentRepository.save(student);
    return modelMapper.map(updatedStudent, StudentDTO.class);
}

    @Override
    @Transactional
    public void deleteStudent(String id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }

    @Override
    public AcademicRecordDTO getAcademicRecord(String studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        List<AcademicRecordDTO.EnrollmentRecordDTO> enrollments = AcademicRecordUtils.fetchEnrollmentRecords(studentId, restTemplate);
        double gpa = AcademicRecordUtils.calculateGPA(enrollments.stream().map(AcademicRecordDTO.EnrollmentRecordDTO::getGrade).collect(Collectors.toList()));

        return new AcademicRecordDTO(student.getId(), student.getName(), enrollments, gpa);
    }
}