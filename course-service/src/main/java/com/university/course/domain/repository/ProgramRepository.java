package com.university.course.domain.repository;

import com.university.course.domain.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository<Program, String> {
    Optional<Program> findByName(String name);
}