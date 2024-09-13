package com.university.course.service;

import com.university.course.api.dto.ProgramDTO;
import com.university.course.api.dto.CreateProgramRequest;
import com.university.course.api.dto.UpdateProgramRequest;

import java.util.List;

public interface ProgramService {
    ProgramDTO createProgram(CreateProgramRequest request);
    ProgramDTO getProgramById(String id);
    List<ProgramDTO> getAllPrograms();
    ProgramDTO updateProgram(String id, UpdateProgramRequest request);
    void deleteProgram(String id);
    ProgramDTO assignCourseToProgram(String programId, String courseId);
    ProgramDTO removeCourseFromProgram(String programId, String courseId);
}