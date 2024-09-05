package com.university.course.service;

import com.university.course.api.dto.ProfessorDTO;
import com.university.course.api.dto.CreateProfessorRequest;
import com.university.course.api.dto.UpdateProfessorRequest;

import java.util.List;

public interface ProfessorService {
    ProfessorDTO createProfessor(CreateProfessorRequest request);
    ProfessorDTO getProfessorById(String id);
    List<ProfessorDTO> getAllProfessors();
    ProfessorDTO updateProfessor(String id, UpdateProfessorRequest request);
    void deleteProfessor(String id);
}
