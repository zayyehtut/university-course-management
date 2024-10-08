package com.university.course.config;

import com.university.course.api.dto.*;
import com.university.course.domain.model.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        configureModelMapper(modelMapper);
        return modelMapper;
    }

    private void configureModelMapper(ModelMapper modelMapper) {
        // Configure Course to CourseDTO mapping
        modelMapper.typeMap(Course.class, CourseDTO.class)
            .addMappings(mapper -> {
                mapper.map(src -> Optional.ofNullable(src.getProfessor()).map(Professor::getId).orElse(null), CourseDTO::setProfessorId);
                mapper.map(src -> Optional.ofNullable(src.getProfessor()).map(Professor::getName).orElse(null), CourseDTO::setProfessorName);
                mapper.map(src -> Optional.ofNullable(src.getType()).map(Enum::name).orElse(null), CourseDTO::setType);
                mapper.map(src -> Optional.ofNullable(src.getTutors()).orElse(Collections.emptySet()).stream()
                        .map(this::mapToSimplifiedTutorDTO)
                        .collect(Collectors.toSet()), CourseDTO::setTutors);
                mapper.map(src -> Optional.ofNullable(src.getTimetables()).orElse(Collections.emptySet()).stream()
                        .map(timetable -> modelMapper.map(timetable, TimetableDTO.class))
                        .collect(Collectors.toSet()), CourseDTO::setTimetables);
                mapper.map(src -> Optional.ofNullable(src.getPrograms()).orElse(Collections.emptySet()).stream()
                        .map(Program::getId)
                        .collect(Collectors.toSet()), CourseDTO::setProgramIds);
            });

        // Configure Professor to ProfessorDTO mapping
        modelMapper.typeMap(Professor.class, ProfessorDTO.class)
            .addMappings(mapper -> 
                mapper.map(src -> Optional.ofNullable(src.getCourses()).orElse(Collections.emptySet()).stream()
                    .map(Course::getId)
                    .collect(Collectors.toList()), ProfessorDTO::setCourseIds)
            );

        // Configure Tutor to TutorDTO mapping
        modelMapper.typeMap(Tutor.class, TutorDTO.class)
            .addMappings(mapper -> {
                mapper.map(Tutor::getId, TutorDTO::setId);
                mapper.map(Tutor::getName, TutorDTO::setName);
                mapper.map(Tutor::getEmail, TutorDTO::setEmail);
                mapper.map(Tutor::getSpecialization, TutorDTO::setSpecialization);
                mapper.map(src -> Optional.ofNullable(src.getCourses()).orElse(Collections.emptySet()).stream()
                    .map(Course::getId)
                    .collect(Collectors.toSet()), TutorDTO::setCourseIds);
            });

        // Configure Timetable to TimetableDTO mapping
        modelMapper.typeMap(Timetable.class, TimetableDTO.class)
            .addMappings(mapper -> 
                mapper.map(src -> Optional.ofNullable(src.getCourse()).map(Course::getId).orElse(null), TimetableDTO::setCourseId)
            );

        // Configure Program to ProgramDTO mapping
        modelMapper.createTypeMap(Program.class, ProgramDTO.class)
            .addMappings(mapper -> {
                mapper.map(Program::getId, ProgramDTO::setId);
                mapper.map(Program::getName, ProgramDTO::setName);
                mapper.map(Program::getDescription, ProgramDTO::setDescription);
                mapper.map(src -> Optional.ofNullable(src.getDegreeType()).map(Enum::name).orElse(null), ProgramDTO::setDegreeType);
                mapper.map(Program::getRequiredCredits, ProgramDTO::setRequiredCredits);
                mapper.map(src -> Optional.ofNullable(src.getCourses()).orElse(Collections.emptySet()).stream()
                        .map(Course::getId)
                        .collect(Collectors.toSet()), ProgramDTO::setCourseIds);
            });

        // Configure CreateProgramRequest to Program mapping
        modelMapper.createTypeMap(CreateProgramRequest.class, Program.class)
            .addMappings(mapper -> {
                mapper.map(CreateProgramRequest::getName, Program::setName);
                mapper.map(CreateProgramRequest::getDescription, Program::setDescription);
                mapper.map(src -> Optional.ofNullable(src.getDegreeType()).map(Program.DegreeType::valueOf).orElse(null), Program::setDegreeType);
                mapper.map(CreateProgramRequest::getRequiredCredits, Program::setRequiredCredits);
            });
    }

    private TutorDTO mapToSimplifiedTutorDTO(Tutor tutor) {
        TutorDTO simplifiedTutorDTO = new TutorDTO();
        simplifiedTutorDTO.setId(tutor.getId());
        simplifiedTutorDTO.setName(tutor.getName());
        simplifiedTutorDTO.setEmail(tutor.getEmail());
        simplifiedTutorDTO.setSpecialization(tutor.getSpecialization());
        // We're not setting courseIds here to avoid circular reference
        return simplifiedTutorDTO;
    }
}