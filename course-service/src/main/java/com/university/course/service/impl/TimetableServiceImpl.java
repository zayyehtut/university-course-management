package com.university.course.service.impl;

import com.university.common.exception.*;
import com.university.common.util.ValidationUtils;
import com.university.course.api.dto.TimetableDTO;
import com.university.course.api.dto.CreateTimetableRequest;
import com.university.course.api.dto.UpdateTimetableRequest;
import com.university.course.domain.model.Course;
import com.university.course.domain.model.Timetable;
import com.university.course.domain.repository.CourseRepository;
import com.university.course.domain.repository.TimetableRepository;
import com.university.course.exception.TimetableNotFoundException;
import com.university.course.service.TimetableService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimetableServiceImpl implements TimetableService {

    private final TimetableRepository timetableRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TimetableServiceImpl(TimetableRepository timetableRepository, CourseRepository courseRepository, ModelMapper modelMapper) {
        this.timetableRepository = timetableRepository;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public TimetableDTO createTimetable(CreateTimetableRequest request) {
        validateCreateTimetableRequest(request);

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + request.getCourseId()));

        Timetable timetable = new Timetable();
        timetable.setCourse(course);
        timetable.setDayOfWeek(DayOfWeek.valueOf(request.getDayOfWeek().toUpperCase()));
        timetable.setStartTime(LocalTime.parse(request.getStartTime(), DateTimeFormatter.ofPattern("HH:mm")));
        timetable.setEndTime(LocalTime.parse(request.getEndTime(), DateTimeFormatter.ofPattern("HH:mm")));
        timetable.setLocation(request.getLocation());

        Timetable savedTimetable = timetableRepository.save(timetable);
        return modelMapper.map(savedTimetable, TimetableDTO.class);
    }


    @Override
    public TimetableDTO getTimetableById(String id) {
        Timetable timetable = timetableRepository.findById(id)
                .orElseThrow(() -> new TimetableNotFoundException(id));
        return modelMapper.map(timetable, TimetableDTO.class);
    }

    @Override
    public List<TimetableDTO> getTimetablesByCourseId(String courseId) {
        List<Timetable> timetables = timetableRepository.findByCourseId(courseId);
        return timetables.stream()
                .map(timetable -> modelMapper.map(timetable, TimetableDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TimetableDTO updateTimetable(String id, UpdateTimetableRequest request) {
        validateUpdateTimetableRequest(request);

        Timetable timetable = timetableRepository.findById(id)
                .orElseThrow(() -> new TimetableNotFoundException(id));

        modelMapper.map(request, timetable);

        if (request.getCourseId() != null && !request.getCourseId().equals(timetable.getCourse().getId())) {
            Course newCourse = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + request.getCourseId()));
            timetable.setCourse(newCourse);
        }

        Timetable updatedTimetable = timetableRepository.save(timetable);
        return modelMapper.map(updatedTimetable, TimetableDTO.class);
    }

    @Override
    @Transactional
    public void deleteTimetable(String id) {
        if (!timetableRepository.existsById(id)) {
            throw new TimetableNotFoundException(id);
        }
        timetableRepository.deleteById(id);
    }

    private void validateCreateTimetableRequest(CreateTimetableRequest request) {
        ValidationUtils.validateNonEmptyString(request.getCourseId(), "Course ID");
        ValidationUtils.validateNonEmptyString(request.getDayOfWeek(), "Day of Week");
        ValidationUtils.validateNonEmptyString(request.getStartTime(), "Start Time");
        ValidationUtils.validateNonEmptyString(request.getEndTime(), "End Time");
        ValidationUtils.validateNonEmptyString(request.getLocation(), "Location");

         // Validate day of week
         try {
            DayOfWeek.valueOf(request.getDayOfWeek().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid day of week: " + request.getDayOfWeek());
        }

        // Validate time format
        try {
            LocalTime.parse(request.getStartTime(), DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime.parse(request.getEndTime(), DateTimeFormatter.ofPattern("HH:mm"));
        } catch (Exception e) {
            throw new ValidationException("Invalid time format. Use HH:mm");
        }
    }

    private void validateUpdateTimetableRequest(UpdateTimetableRequest request) {
        if (request.getCourseId() != null) {
            ValidationUtils.validateNonEmptyString(request.getCourseId(), "Course ID");
        }
        if (request.getDayOfWeek() != null) {
            ValidationUtils.validateNonEmptyString(request.getDayOfWeek(), "Day of Week");
        }
        if (request.getStartTime() != null) {
            ValidationUtils.validateNonEmptyString(request.getStartTime(), "Start Time");
        }
        if (request.getEndTime() != null) {
            ValidationUtils.validateNonEmptyString(request.getEndTime(), "End Time");
        }
        if (request.getLocation() != null) {
            ValidationUtils.validateNonEmptyString(request.getLocation(), "Location");
        }
    }
}


