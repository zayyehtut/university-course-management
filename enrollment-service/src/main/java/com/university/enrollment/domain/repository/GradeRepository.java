// File: enrollment-service/src/main/java/com/university/enrollment/domain/repository/GradeRepository.java
package com.university.enrollment.domain.repository;

import com.university.enrollment.domain.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, String> {
    List<Grade> findByEnrollmentId(String enrollmentId);
}