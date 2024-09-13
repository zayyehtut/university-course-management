package com.university.course.service.impl;

import com.university.common.exception.*;
import com.university.common.util.ValidationUtils;
import com.university.course.api.dto.TutorDTO;
import com.university.course.api.dto.CreateTutorRequest;
import com.university.course.api.dto.UpdateTutorRequest;
import com.university.course.domain.model.Course;
import com.university.course.domain.model.Tutor;
import com.university.course.domain.repository.CourseRepository;
import com.university.course.domain.repository.TutorRepository;
import com.university.course.exception.TutorNotFoundException;
import com.university.course.service.TutorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TutorServiceImpl implements TutorService {

    private final TutorRepository tutorRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TutorServiceImpl(TutorRepository tutorRepository, CourseRepository courseRepository, ModelMapper modelMapper) {
        this.tutorRepository = tutorRepository;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    private TutorDTO mapTutorToDTO(Tutor tutor) {
        TutorDTO dto = modelMapper.map(tutor, TutorDTO.class);
        dto.setCourseIds(tutor.getCourses().stream()
                .map(Course::getId)
                .collect(Collectors.toSet()));
        return dto;
    }


    @Override
    @Transactional
    public TutorDTO createTutor(CreateTutorRequest request) {
        validateCreateTutorRequest(request);

        if (tutorRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Tutor with this email already exists");
        }

        Tutor tutor = modelMapper.map(request, Tutor.class);
        Tutor savedTutor = tutorRepository.save(tutor);
        return mapTutorToDTO(savedTutor);
    }

    @Override
    public TutorDTO getTutorById(String id) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new TutorNotFoundException(id));
        return mapTutorToDTO(tutor);
    }

    @Override
    public List<TutorDTO> getAllTutors() {
        return tutorRepository.findAll().stream()
        .map(this::mapTutorToDTO)
        .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TutorDTO updateTutor(String id, UpdateTutorRequest request) {
        validateUpdateTutorRequest(request);

        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new TutorNotFoundException(id));

        if (request.getEmail() != null && !request.getEmail().equals(tutor.getEmail())) {
            if (tutorRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new DuplicateResourceException("Tutor with this email already exists");
            }
        }

        modelMapper.map(request, tutor);
        Tutor updatedTutor = tutorRepository.save(tutor);
        return mapTutorToDTO(updatedTutor);
    }

    @Override
    @Transactional
    public void deleteTutor(String id) {
        if (!tutorRepository.existsById(id)) {
            throw new TutorNotFoundException(id);
        }
        tutorRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void assignTutorToCourse(String tutorId, String courseId) {
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new TutorNotFoundException(tutorId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        course.addTutor(tutor);
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void removeTutorFromCourse(String tutorId, String courseId) {
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new TutorNotFoundException(tutorId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        course.removeTutor(tutor);
        courseRepository.save(course);
    }

    private void validateCreateTutorRequest(CreateTutorRequest request) {
        ValidationUtils.validateNonEmptyString(request.getName(), "Name");
        ValidationUtils.validateEmail(request.getEmail());
        ValidationUtils.validateNonEmptyString(request.getSpecialization(), "Specialization");
    }

    private void validateUpdateTutorRequest(UpdateTutorRequest request) {
        if (request.getName() != null) {
            ValidationUtils.validateNonEmptyString(request.getName(), "Name");
        }
        if (request.getEmail() != null) {
            ValidationUtils.validateEmail(request.getEmail());
        }
        if (request.getSpecialization() != null) {
            ValidationUtils.validateNonEmptyString(request.getSpecialization(), "Specialization");
        }
    }
}