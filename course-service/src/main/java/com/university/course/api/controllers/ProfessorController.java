package com.university.course.api.controllers;

import com.university.course.api.dto.ProfessorDTO;
import com.university.course.api.dto.CreateProfessorRequest;
import com.university.course.api.dto.UpdateProfessorRequest;
import com.university.course.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professors")
public class ProfessorController {

    private final ProfessorService professorService;

    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping
    public ResponseEntity<ProfessorDTO> createProfessor(@Valid @RequestBody CreateProfessorRequest request) {
        ProfessorDTO createdProfessor = professorService.createProfessor(request);
        return new ResponseEntity<>(createdProfessor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDTO> getProfessor(@PathVariable String id) {
        ProfessorDTO professor = professorService.getProfessorById(id);
        return ResponseEntity.ok(professor);
    }

    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> getAllProfessors() {
        List<ProfessorDTO> professors = professorService.getAllProfessors();
        return ResponseEntity.ok(professors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDTO> updateProfessor(@PathVariable String id, @Valid @RequestBody UpdateProfessorRequest request) {
        ProfessorDTO updatedProfessor = professorService.updateProfessor(id, request);
        return ResponseEntity.ok(updatedProfessor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable String id) {
        professorService.deleteProfessor(id);
        return ResponseEntity.noContent().build();
    }
}