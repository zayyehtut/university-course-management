package com.university.course.service.impl;

import com.university.common.exception.*;
import com.university.course.api.dto.ProfessorDTO;
import com.university.course.api.dto.CreateProfessorRequest;
import com.university.course.api.dto.UpdateProfessorRequest;
import com.university.course.domain.model.Professor;
import com.university.course.domain.repository.ProfessorRepository;
import com.university.course.exception.ProfessorNotFoundException;
import com.university.course.service.ProfessorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProfessorServiceImpl(ProfessorRepository professorRepository, ModelMapper modelMapper) {
        this.professorRepository = professorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public ProfessorDTO createProfessor(CreateProfessorRequest request) {
        validateCreateProfessorRequest(request);

        Professor professor = new Professor();
        professor.setName(request.getName());
        professor.setDepartment(request.getDepartment());

        Professor savedProfessor = professorRepository.save(professor);
        return modelMapper.map(savedProfessor, ProfessorDTO.class);
    }

    @Override
    public ProfessorDTO getProfessorById(String id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ProfessorNotFoundException(id));
        return modelMapper.map(professor, ProfessorDTO.class);
    }

    @Override
    public List<ProfessorDTO> getAllProfessors() {
        return professorRepository.findAll().stream()
                .map(professor -> modelMapper.map(professor, ProfessorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProfessorDTO updateProfessor(String id, UpdateProfessorRequest request) {
        validateUpdateProfessorRequest(request);
    
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ProfessorNotFoundException(id));
    
        if (request.getName() != null) {
            professor.setName(request.getName());
        }
        if (request.getDepartment() != null) {
            professor.setDepartment(request.getDepartment());
        }
    
        Professor updatedProfessor = professorRepository.save(professor);
        return modelMapper.map(updatedProfessor, ProfessorDTO.class);
    }
    

    @Override
    @Transactional
    public void deleteProfessor(String id) {
        if (!professorRepository.existsById(id)) {
            throw new ProfessorNotFoundException(id);
        }
        professorRepository.deleteById(id);
    }

    private void validateCreateProfessorRequest(CreateProfessorRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new ValidationException("Professor name is required");
        }
        if (request.getDepartment() == null || request.getDepartment().trim().isEmpty()) {
            throw new ValidationException("Department is required");
        }
    }

    private void validateUpdateProfessorRequest(UpdateProfessorRequest request) {
        if (request.getName() != null && request.getName().trim().isEmpty()) {
            throw new ValidationException("Professor name cannot be empty");
        }
        if (request.getDepartment() != null && request.getDepartment().trim().isEmpty()) {
            throw new ValidationException("Department cannot be empty");
        }
    }
}