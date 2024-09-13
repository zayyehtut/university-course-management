package com.university.course.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import com.university.course.config.*;

import com.university.common.util.*;
import com.university.common.exception.*;

import com.university.course.api.dto.*;
import com.university.course.domain.model.*;
import com.university.course.domain.repository.*;
import com.university.course.exception.*;
import com.university.course.service.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;;

@Service
public class CourseServiceImpl implements CourseService {

    @PersistenceContext
    private EntityManager entityManager;

    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;
    private final TutorRepository tutorRepository;
    private final TimetableRepository timetableRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, 
                             ProfessorRepository professorRepository,
                             TutorRepository tutorRepository,
                             TimetableRepository timetableRepository,
                             ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
        this.tutorRepository = tutorRepository;
        this.timetableRepository = timetableRepository;
        this.modelMapper = modelMapper;
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
            
                // Save the course first to ensure it has an ID
                course = courseRepository.save(course);

        if (request.getTutorIds() != null && !request.getTutorIds().isEmpty()) {
            Set<Tutor> tutors = request.getTutorIds().stream()
                    .map(id -> tutorRepository.findById(id)
                            .orElseThrow(() -> new TutorNotFoundException(id)))
                    .collect(Collectors.toSet());


            for (Tutor tutor : tutors) {
                course.addTutor(tutor);
                tutorRepository.save(tutor);  // Save each tutor to update the relationship
            }
        }

         // Save the course again to update the relationships
        course = courseRepository.save(course);
        return mapCourseToDTO(course);
        
    }
    
    @Override
    public CourseDTO getCourseById(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        return mapCourseToDTO(course);
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::mapCourseToDTO)
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
        if (request.getTutorIds() != null) {

            course.getTutors().forEach(tutor -> {
                if (!request.getTutorIds().contains(tutor.getId())) {
                    tutor.getCourses().remove(course);
                }
            });

            Set<Tutor> newTutors = request.getTutorIds().stream()
                    .map(tutorId -> tutorRepository.findById(tutorId)
                            .orElseThrow(() -> new TutorNotFoundException(tutorId)))
                    .collect(Collectors.toSet());
            
            // Add this course to new tutors
            newTutors.forEach(tutor -> {
                if (!course.getTutors().contains(tutor)) {
                    tutor.getCourses().add(course);
                }
            });

            course.setTutors(newTutors);
        }

        Course updatedCourse = courseRepository.save(course);
        return mapCourseToDTO(updatedCourse);
    }
    
    @Override
    @Transactional
    public void deleteCourse(String id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        courseRepository.deleteById(id);
    }

    

    
    @Override
    @Transactional
    public CourseDTO assignTutorToCourse(String courseId, String tutorId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new TutorNotFoundException(tutorId));

        course.addTutor(tutor);
        Course updatedCourse = courseRepository.save(course);
        return mapCourseToDTO(updatedCourse);
    }

    
    @Override
    public CourseDTO removeTutorFromCourse(String courseId, String tutorId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new TutorNotFoundException(tutorId));

        course.removeTutor(tutor);
        Course updatedCourse = courseRepository.save(course);
        return mapCourseToDTO(updatedCourse);
    }

    @Override
    public List<TutorDTO> getTutorsByCourseId(String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        return course.getTutors().stream()
                .map(this::mapTutorToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TimetableDTO addTimetableToCourse(String courseId, CreateTimetableRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        Timetable timetable = new Timetable();
        timetable.setCourse(course);
        timetable.setDayOfWeek(DayOfWeek.valueOf(request.getDayOfWeek().toUpperCase()));
        timetable.setStartTime(LocalTime.parse(request.getStartTime(), DateTimeFormatter.ofPattern("HH:mm")));
        timetable.setEndTime(LocalTime.parse(request.getEndTime(), DateTimeFormatter.ofPattern("HH:mm")));
        timetable.setLocation(request.getLocation());

        course.addTimetable(timetable);
        Course savedCourse = courseRepository.save(course);

        Timetable savedTimetable = savedCourse.getTimetables().stream()
                .filter(t -> t.getDayOfWeek().equals(timetable.getDayOfWeek()) &&
                             t.getStartTime().equals(timetable.getStartTime()) &&
                             t.getEndTime().equals(timetable.getEndTime()) &&
                             t.getLocation().equals(timetable.getLocation()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Timetable not found after saving"));

        return modelMapper.map(savedTimetable, TimetableDTO.class);
    }

    @Override
    @Transactional
    public void removeTimetableFromCourse(String courseId, String timetableId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        Timetable timetable = timetableRepository.findById(timetableId)
                .orElseThrow(() -> new TimetableNotFoundException(timetableId));

        if (!course.getTimetables().contains(timetable)) {
            throw new IllegalStateException("Timetable is not associated with this course");
        }

        course.removeTimetable(timetable);
        courseRepository.save(course);
        timetableRepository.delete(timetable);
    }

    @Override
    public List<TimetableDTO> getTimetablesByCourseId(String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        return course.getTimetables().stream()
                .map(timetable -> modelMapper.map(timetable, TimetableDTO.class))
                .collect(Collectors.toList());
    }
    private void validateCreateCourseRequest(CreateCourseRequest request) {
        ValidationUtils.validateNonEmptyString(request.getCode(), "Course code");
        ValidationUtils.validateNonEmptyString(request.getName(), "Course name");
        if (request.getCredits() <= 0) {
            throw new ValidationException("Credits must be a positive number");
        }
        ValidationUtils.validateType(request.getType(), new String[]{"MANDATORY", "ELECTIVE"}, "Type");
        ValidationUtils.validateNonEmptyString(request.getProfessorId(), "Professor ID");
    }

    private void validateUpdateCourseRequest(UpdateCourseRequest request) {
        if (request.getName() != null) {
            ValidationUtils.validateNonEmptyString(request.getName(), "Course name");
        }
        if (request.getCredits() != null && request.getCredits() <= 0) {
            throw new ValidationException("Credits must be a positive number");
        }
        if (request.getType() != null) {
            ValidationUtils.validateType(request.getType(), new String[]{"MANDATORY", "ELECTIVE"}, "Type");
        }
    }

    private CourseDTO mapCourseToDTO(Course course) {
        CourseDTO dto = modelMapper.map(course, CourseDTO.class);
        dto.setTutors(course.getTutors().stream()
                .map(this::mapTutorToDTO)
                .collect(Collectors.toSet()));
        return dto;
    }

    private TutorDTO mapTutorToDTO(Tutor tutor) {
        TutorDTO dto = modelMapper.map(tutor, TutorDTO.class);
        dto.setCourseIds(tutor.getCourses().stream()
                .map(Course::getId)
                .collect(Collectors.toSet()));
        return dto;
    }
}