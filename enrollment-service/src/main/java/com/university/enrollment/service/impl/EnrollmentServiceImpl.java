package com.university.enrollment.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.university.common.exception.*;
import com.university.common.util.ValidationUtils;
import com.university.common.dto.EnrollmentDTO;

import com.university.enrollment.exception.EnrollmentNotFoundException;
import com.university.enrollment.exception.GradeNotFoundException;


import com.university.enrollment.api.dto.GradeDTO;
import com.university.enrollment.api.dto.CreateEnrollmentRequest;
import com.university.enrollment.api.dto.UpdateGradeRequest;
import com.university.enrollment.domain.model.Enrollment;
import com.university.enrollment.domain.model.Grade;
import com.university.enrollment.domain.repository.EnrollmentRepository;
import com.university.enrollment.domain.repository.GradeRepository;
import com.university.enrollment.service.EnrollmentService;


import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final GradeRepository gradeRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);

    //private static final int MAX_ENROLLMENTS_PER_SEMESTER = 6;


    @Autowired
    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, GradeRepository gradeRepository, ModelMapper modelMapper) {
        
        logger.debug("Initializing EnrollmentServiceImpl");
        this.enrollmentRepository = enrollmentRepository;
        this.gradeRepository = gradeRepository;
        this.modelMapper = modelMapper;
        try {
            configureModelMapper();
        } catch (Exception e) {
            logger.error("Error configuring ModelMapper", e);
            throw e;
        }
    }

    private void configureModelMapper() {
        logger.debug("Starting ModelMapper configuration");

        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT)
            .setFieldMatchingEnabled(true)
            .setSkipNullEnabled(true)
            .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        // CreateEnrollmentRequest to Enrollment mapping
        TypeMap<CreateEnrollmentRequest, Enrollment> createRequestMap = modelMapper.createTypeMap(CreateEnrollmentRequest.class, Enrollment.class);
        createRequestMap.addMappings(mapper -> {
            mapper.skip(Enrollment::setId);
            mapper.map(CreateEnrollmentRequest::getStudentId, Enrollment::setStudentId);
            mapper.map(CreateEnrollmentRequest::getCourseId, Enrollment::setCourseId);
            mapper.map(CreateEnrollmentRequest::getCourseName, Enrollment::setCourseName);
            mapper.map(CreateEnrollmentRequest::getSemester, Enrollment::setSemester);
            mapper.skip(Enrollment::setEnrollmentDate);
            mapper.skip(Enrollment::setStatus);
            mapper.skip(Enrollment::setGrades);
        });

        // Enrollment to EnrollmentDTO mapping
        TypeMap<Enrollment, EnrollmentDTO> enrollmentMap = modelMapper.createTypeMap(Enrollment.class, EnrollmentDTO.class);
        enrollmentMap.addMappings(mapper -> {
            mapper.map(Enrollment::getId, EnrollmentDTO::setId);
            mapper.map(Enrollment::getStudentId, EnrollmentDTO::setStudentId);
            mapper.map(Enrollment::getCourseId, EnrollmentDTO::setCourseId);
            mapper.map(Enrollment::getCourseName, EnrollmentDTO::setCourseName);
            mapper.map(Enrollment::getEnrollmentDate, EnrollmentDTO::setEnrollmentDate);
            mapper.map(Enrollment::getSemester, EnrollmentDTO::setSemester);
            mapper.map(src -> src.getStatus() != null ? src.getStatus().name() : null, EnrollmentDTO::setStatus);
            mapper.map(Enrollment::getGrade, EnrollmentDTO::setGrade);
        });

        // Grade to GradeDTO mapping
        TypeMap<Grade, GradeDTO> gradeMap = modelMapper.createTypeMap(Grade.class, GradeDTO.class);
        gradeMap.addMappings(mapper -> {
            mapper.map(Grade::getId, GradeDTO::setId);
            mapper.map(src -> src.getEnrollment().getId(), GradeDTO::setEnrollmentId);
            mapper.map(Grade::getLetterGrade, GradeDTO::setLetterGrade);
            mapper.map(Grade::getNumericGrade, GradeDTO::setNumericGrade);
        });

        // UpdateGradeRequest to Grade mapping
        TypeMap<UpdateGradeRequest, Grade> updateGradeMap = modelMapper.createTypeMap(UpdateGradeRequest.class, Grade.class);
        updateGradeMap.addMappings(mapper -> {
            mapper.skip(Grade::setId);
            mapper.skip(Grade::setEnrollment);
            mapper.map(UpdateGradeRequest::getLetterGrade, Grade::setLetterGrade);
            mapper.map(UpdateGradeRequest::getNumericGrade, Grade::setNumericGrade);
            mapper.skip(Grade::setDateRecorded);
        });
    }


    @Override
    @Transactional
    public EnrollmentDTO createEnrollment(CreateEnrollmentRequest request) {
        validateCreateEnrollmentRequest(request);

        
        Enrollment enrollment = modelMapper.map(request, Enrollment.class);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setStatus(Enrollment.EnrollmentStatus.ACTIVE);

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return modelMapper.map(savedEnrollment, EnrollmentDTO.class);
    }

    @Override
    public EnrollmentDTO getEnrollmentById(String id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));
        return modelMapper.map(enrollment, EnrollmentDTO.class);
    }


    @Override
    public List<EnrollmentDTO> getAllEnrollments() {
        return enrollmentRepository.findAll().stream()
                .map(enrollment -> modelMapper.map(enrollment, EnrollmentDTO.class))
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
        return modelMapper.map(updatedEnrollment, EnrollmentDTO.class);
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

        Grade grade = modelMapper.map(request, Grade.class);
        grade.setEnrollment(enrollment);
        grade.setDateRecorded(LocalDate.now());

        Grade savedGrade = gradeRepository.save(grade);

        // Update the enrollment's grade
        enrollment.setGrade(savedGrade.getLetterGrade());
        enrollmentRepository.save(enrollment);

        return modelMapper.map(savedGrade, GradeDTO.class);
    }

    @Override
    @Transactional
    public GradeDTO updateGrade(String gradeId, UpdateGradeRequest request) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new GradeNotFoundException(gradeId));

        validateGradeRequest(request);

        modelMapper.map(request, grade);

        Grade updatedGrade = gradeRepository.save(grade);

        // Update the associated enrollment's grade
        Enrollment enrollment = updatedGrade.getEnrollment();
        enrollment.setGrade(updatedGrade.getLetterGrade());
        enrollmentRepository.save(enrollment);

        
        return modelMapper.map(updatedGrade, GradeDTO.class);
    }

    @Override
    public GradeDTO getGradeById(String id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new GradeNotFoundException(id));
        return modelMapper.map(grade, GradeDTO.class);
    }

    @Override
    public List<GradeDTO> getGradesByEnrollmentId(String enrollmentId) {
        return gradeRepository.findByEnrollment_Id(enrollmentId).stream()
                .map(grade -> modelMapper.map(grade, GradeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentDTO> getEnrollmentsByStudentId(String studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        return enrollments.stream()
                .map(enrollment -> modelMapper.map(enrollment, EnrollmentDTO.class))
                .collect(Collectors.toList());
    }

    private void validateCreateEnrollmentRequest(CreateEnrollmentRequest request) {
        ValidationUtils.validateNonEmptyString(request.getStudentId(), "Student ID");
        ValidationUtils.validateNonEmptyString(request.getCourseId(), "Course ID");
    }

    private void validateGradeRequest(UpdateGradeRequest request) {
        ValidationUtils.validateNonEmptyString(request.getLetterGrade(), "Letter grade");
        if (request.getNumericGrade() < 0 || request.getNumericGrade() > 4.0) {
            throw new ValidationException("Numeric grade must be between 0 and 4.0");
        }
    }
}