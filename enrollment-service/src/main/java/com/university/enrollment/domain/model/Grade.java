package com.university.enrollment.domain.model;

import jakarta.persistence.*;

@Entity
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String enrollmentId;

    @Column(nullable = false)
    private String letterGrade;

    @Column(nullable = false)
    private float numericGrade;

    // Constructors, getters, setters, etc.
}