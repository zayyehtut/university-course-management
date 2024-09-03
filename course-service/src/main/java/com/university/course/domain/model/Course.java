package com.university.course.domain.model;

import jakarta.persistence.*;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int credits;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseType type;

    // Constructors, getters, setters, etc.

    public enum CourseType {
        MANDATORY, ELECTIVE
    }
}