package com.university.course.service;

import com.university.course.api.dto.TimetableDTO;
import com.university.course.api.dto.CreateTimetableRequest;
import com.university.course.api.dto.UpdateTimetableRequest;

import java.util.List;

public interface TimetableService {
    TimetableDTO createTimetable(CreateTimetableRequest request);
    TimetableDTO getTimetableById(String id);
    List<TimetableDTO> getTimetablesByCourseId(String courseId);
    TimetableDTO updateTimetable(String id, UpdateTimetableRequest request);
    void deleteTimetable(String id);
}
