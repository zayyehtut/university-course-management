package com.university.student.domain.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    // Constructors, getters, setters, etc.
}