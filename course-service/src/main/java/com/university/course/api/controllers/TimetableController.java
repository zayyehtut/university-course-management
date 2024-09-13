package com.university.course.api.controllers;

import com.university.course.api.dto.TimetableDTO;
import com.university.course.api.dto.CreateTimetableRequest;
import com.university.course.api.dto.UpdateTimetableRequest;
import com.university.course.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timetables")
public class TimetableController {

    private final TimetableService timetableService;

    @Autowired
    public TimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @PostMapping
    public ResponseEntity<TimetableDTO> createTimetable(@RequestBody CreateTimetableRequest request) {
        TimetableDTO createdTimetable = timetableService.createTimetable(request);
        return new ResponseEntity<>(createdTimetable, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimetableDTO> getTimetable(@PathVariable String id) {
        TimetableDTO timetable = timetableService.getTimetableById(id);
        return ResponseEntity.ok(timetable);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<TimetableDTO>> getTimetablesByCourseId(@PathVariable String courseId) {
        List<TimetableDTO> timetables = timetableService.getTimetablesByCourseId(courseId);
        return ResponseEntity.ok(timetables);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimetableDTO> updateTimetable(@PathVariable String id, @RequestBody UpdateTimetableRequest request) {
        TimetableDTO updatedTimetable = timetableService.updateTimetable(id, request);
        return ResponseEntity.ok(updatedTimetable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimetable(@PathVariable String id) {
        timetableService.deleteTimetable(id);
        return ResponseEntity.noContent().build();
    }
}