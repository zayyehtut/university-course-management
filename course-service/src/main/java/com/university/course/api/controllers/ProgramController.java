package com.university.course.api.controllers;

import com.university.course.api.dto.ProgramDTO;
import com.university.course.api.dto.CreateProgramRequest;
import com.university.course.api.dto.UpdateProgramRequest;
import com.university.course.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programs")
public class ProgramController {

    private final ProgramService programService;

    @Autowired
    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @PostMapping
    public ResponseEntity<ProgramDTO> createProgram(@RequestBody CreateProgramRequest request) {
        ProgramDTO createdProgram = programService.createProgram(request);
        return new ResponseEntity<>(createdProgram, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramDTO> getProgram(@PathVariable String id) {
        ProgramDTO program = programService.getProgramById(id);
        return ResponseEntity.ok(program);
    }

    @GetMapping
    public ResponseEntity<List<ProgramDTO>> getAllPrograms() {
        List<ProgramDTO> programs = programService.getAllPrograms();
        return ResponseEntity.ok(programs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramDTO> updateProgram(@PathVariable String id, @RequestBody UpdateProgramRequest request) {
        ProgramDTO updatedProgram = programService.updateProgram(id, request);
        return ResponseEntity.ok(updatedProgram);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable String id) {
        programService.deleteProgram(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{programId}/courses/{courseId}")
    public ResponseEntity<ProgramDTO> assignCourseToProgram(@PathVariable String programId, @PathVariable String courseId) {
        ProgramDTO updatedProgram = programService.assignCourseToProgram(programId, courseId);
        return ResponseEntity.ok(updatedProgram);
    }

    @DeleteMapping("/{programId}/courses/{courseId}")
    public ResponseEntity<ProgramDTO> removeCourseFromProgram(@PathVariable String programId, @PathVariable String courseId) {
        ProgramDTO updatedProgram = programService.removeCourseFromProgram(programId, courseId);
        return ResponseEntity.ok(updatedProgram);
    }
}