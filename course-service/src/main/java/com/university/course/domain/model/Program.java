package com.university.course.domain.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "programs")
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DegreeType degreeType;

    @Column(nullable = false)
    private int requiredCredits;

    @ManyToMany
    @JoinTable(
        name = "program_courses",
        joinColumns = @JoinColumn(name = "program_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();

    public enum DegreeType {
        ASSOCIATE, BACHELOR, MASTER, DOCTORATE
    }

    // Constructors, getters, setters, and other methods

    public Program() {
    }

    public Program(String name, String description, DegreeType degreeType, int requiredCredits) {
        this.name = name;
        this.description = description;
        this.degreeType = degreeType;
        this.requiredCredits = requiredCredits;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DegreeType getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(DegreeType degreeType) {
        this.degreeType = degreeType;
    }

    public int getRequiredCredits() {
        return requiredCredits;
    }

    public void setRequiredCredits(int requiredCredits) {
        this.requiredCredits = requiredCredits;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
        course.getPrograms().add(this);
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
        course.getPrograms().remove(this);
    }
}