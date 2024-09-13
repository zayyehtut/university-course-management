package com.university.course.domain.repository;

import com.university.course.domain.model.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, String> {
    List<Timetable> findByCourseId(String courseId);
}