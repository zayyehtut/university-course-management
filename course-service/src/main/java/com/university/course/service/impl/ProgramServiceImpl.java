package com.university.course.service.impl;

import com.university.common.exception.*;
import com.university.course.api.dto.ProgramDTO;
import com.university.course.api.dto.CreateProgramRequest;
import com.university.course.api.dto.UpdateProgramRequest;
import com.university.course.domain.model.Program;
import com.university.course.domain.model.Course;
import com.university.course.domain.repository.ProgramRepository;
import com.university.course.domain.repository.CourseRepository;
import com.university.course.service.ProgramService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProgramServiceImpl(ProgramRepository programRepository, CourseRepository courseRepository, ModelMapper modelMapper) {
        this.programRepository = programRepository;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    private ProgramDTO mapProgramToDTO(Program program) {
        ProgramDTO dto = modelMapper.map(program, ProgramDTO.class);
        dto.setCourseIds(program.getCourses().stream()
                .map(Course::getId)
                .collect(Collectors.toSet()));
        return dto;
    }

    @Override
    @Transactional
    public ProgramDTO createProgram(CreateProgramRequest request) {
        validateCreateProgramRequest(request);

        if (programRepository.findByName(request.getName()).isPresent()) {
            throw new DuplicateResourceException("Program with this name already exists");
        }

        Program program = modelMapper.map(request, Program.class);
        Program savedProgram = programRepository.save(program);
        return mapProgramToDTO(savedProgram);
    }

    @Override
public ProgramDTO getProgramById(String id) {
    Program program = programRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Program not found with id: " + id));
    return mapProgramToDTO(program);
}

@Override
public List<ProgramDTO> getAllPrograms() {
    return programRepository.findAll().stream()
            .map(this::mapProgramToDTO)
            .collect(Collectors.toList());
}


    @Override
    @Transactional
    public ProgramDTO updateProgram(String id, UpdateProgramRequest request) {
        validateUpdateProgramRequest(request);

        Program program = programRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found with id: " + id));

        if (request.getName() != null && !request.getName().equals(program.getName())) {
            if (programRepository.findByName(request.getName()).isPresent()) {
                throw new DuplicateResourceException("Program with this name already exists");
            }
            program.setName(request.getName());
        }

        if (request.getDescription() != null) {
            program.setDescription(request.getDescription());
        }

        if (request.getDegreeType() != null) {
            program.setDegreeType(Program.DegreeType.valueOf(request.getDegreeType()));
        }

        if (request.getRequiredCredits() != null) {
            program.setRequiredCredits(request.getRequiredCredits());
        }

        Program updatedProgram = programRepository.save(program);
        return mapProgramToDTO(updatedProgram);
    }

    @Override
    @Transactional
    public void deleteProgram(String id) {
        if (!programRepository.existsById(id)) {
            throw new ResourceNotFoundException("Program not found with id: " + id);
        }
        programRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ProgramDTO assignCourseToProgram(String programId, String courseId) {
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found with id: " + programId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        program.addCourse(course);
        Program updatedProgram = programRepository.save(program);
        return mapProgramToDTO(updatedProgram);
    }

    @Override
    @Transactional
    public ProgramDTO removeCourseFromProgram(String programId, String courseId) {
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found with id: " + programId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        program.removeCourse(course);
        Program updatedProgram = programRepository.save(program);
        return mapProgramToDTO(updatedProgram);
    }

    private void validateCreateProgramRequest(CreateProgramRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new ValidationException("Program name is required");
        }
        if (request.getDescription() == null || request.getDescription().trim().isEmpty()) {
            throw new ValidationException("Program description is required");
        }
        if (request.getDegreeType() == null) {
            throw new ValidationException("Degree type is required");
        }
        try {
            Program.DegreeType.valueOf(request.getDegreeType());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid degree type");
        }
        if (request.getRequiredCredits() <= 0) {
            throw new ValidationException("Required credits must be a positive number");
        }
    }

    private void validateUpdateProgramRequest(UpdateProgramRequest request) {
        if (request.getName() != null && request.getName().trim().isEmpty()) {
            throw new ValidationException("Program name cannot be empty");
        }
        if (request.getDescription() != null && request.getDescription().trim().isEmpty()) {
            throw new ValidationException("Program description cannot be empty");
        }
        if (request.getDegreeType() != null) {
            try {
                Program.DegreeType.valueOf(request.getDegreeType());
            } catch (IllegalArgumentException e) {
                throw new ValidationException("Invalid degree type");
            }
        }
        if (request.getRequiredCredits() != null && request.getRequiredCredits() <= 0) {
            throw new ValidationException("Required credits must be a positive number");
        }
    }
}