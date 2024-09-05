package com.university.course.service.impl;

import com.university.course.api.dto.CourseDTO;
import com.university.course.api.dto.CreateCourseRequest;
import com.university.course.api.dto.UpdateCourseRequest;
import com.university.course.domain.model.Course;
import com.university.course.domain.model.Professor;
import com.university.course.domain.repository.CourseRepository;
import com.university.course.domain.repository.ProfessorRepository;
import com.university.course.exception.CourseNotFoundException;
import com.university.course.exception.DuplicateCourseCodeException;
import com.university.course.exception.ProfessorNotFoundException;
import com.university.course.exception.ValidationException;
import com.university.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, ProfessorRepository professorRepository) {
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    @Transactional
    public CourseDTO createCourse(CreateCourseRequest request) {
        validateCreateCourseRequest(request);

        if (courseRepository.findByCode(request.getCode()).isPresent()) {
            throw new DuplicateCourseCodeException(request.getCode());
        }

        Professor professor = professorRepository.findById(request.getProfessorId())
                .orElseThrow(() -> new ProfessorNotFoundException(request.getProfessorId()));

        Course course = new Course();
        course.setCode(request.getCode());
        course.setName(request.getName());
        course.setCredits(request.getCredits());
        course.setType(Course.CourseType.valueOf(request.getType()));
        course.setProfessor(professor);

        Course savedCourse = courseRepository.save(course);
        return convertToDTO(savedCourse);
    }

    @Override
    public CourseDTO getCourseById(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        return convertToDTO(course);
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CourseDTO updateCourse(String id, UpdateCourseRequest request) {
        validateUpdateCourseRequest(request);

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));

        if (request.getName() != null) {
            course.setName(request.getName());
        }
        if (request.getCredits() != null) {
            course.setCredits(request.getCredits());
        }
        if (request.getType() != null) {
            course.setType(Course.CourseType.valueOf(request.getType()));
        }
        if (request.getProfessorId() != null) {
            Professor professor = professorRepository.findById(request.getProfessorId())
                    .orElseThrow(() -> new ProfessorNotFoundException(request.getProfessorId()));
            course.setProfessor(professor);
        }

        Course updatedCourse = courseRepository.save(course);
        return convertToDTO(updatedCourse);
    }

    @Override
    @Transactional
    public void deleteCourse(String id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        courseRepository.deleteById(id);
    }

    private void validateCreateCourseRequest(CreateCourseRequest request) {
        if (request.getCode() == null || request.getCode().trim().isEmpty()) {
            throw new ValidationException("Course code is required");
        }
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new ValidationException("Course name is required");
        }
        if (request.getCredits() <= 0) {
            throw new ValidationException("Credits must be a positive number");
        }
        if (request.getType() == null || (!request.getType().equals("MANDATORY") && !request.getType().equals("ELECTIVE"))) {
            throw new ValidationException("Type must be either MANDATORY or ELECTIVE");
        }
        if (request.getProfessorId() == null || request.getProfessorId().trim().isEmpty()) {
            throw new ValidationException("Professor ID is required");
        }
    }

    private void validateUpdateCourseRequest(UpdateCourseRequest request) {
        if (request.getName() != null && request.getName().trim().isEmpty()) {
            throw new ValidationException("Course name cannot be empty");
        }
        if (request.getCredits() != null && request.getCredits() <= 0) {
            throw new ValidationException("Credits must be a positive number");
        }
        if (request.getType() != null && (!request.getType().equals("MANDATORY") && !request.getType().equals("ELECTIVE"))) {
            throw new ValidationException("Type must be either MANDATORY or ELECTIVE");
        }
    }

    private CourseDTO convertToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setCode(course.getCode());
        dto.setName(course.getName());
        dto.setCredits(course.getCredits());
        dto.setType(course.getType().name());
        if (course.getProfessor() != null) {
            dto.setProfessorId(course.getProfessor().getId());
            dto.setProfessorName(course.getProfessor().getName());
        }
        return dto;
    }
}