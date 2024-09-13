package com.university.course.domain.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "courses")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToMany
    
    

    @JoinTable(
        name = "course_tutors",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "tutor_id")
    )
    @JsonIgnore 
    private Set<Tutor> tutors = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Timetable> timetables = new HashSet<>();

    public enum CourseType {
        MANDATORY, ELECTIVE
    }

    // No-argument constructor
    public Course() {
        this.tutors = new HashSet<>();
    }

    // Parameterized constructor
    public Course(String id, String code, String name, int credits, CourseType type, Professor professor) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.type = type;
        this.professor = professor;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public CourseType getType() {
        return type;
    }

    public void setType(CourseType type) {
        this.type = type;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Set<Tutor> getTutors() {
        return tutors;
    }

    public void setTutors(Set<Tutor> tutors) {
        this.tutors = tutors;
    }

    public void addTutor(Tutor tutor) {
        this.tutors.add(tutor);
        tutor.getCourses().add(this);
    }

    public void removeTutor(Tutor tutor) {
        this.tutors.remove(tutor);
        tutor.getCourses().remove(this);
    }

    public Set<Timetable> getTimetables() {
        return timetables;
    }

    public void setTimetables(Set<Timetable> timetables) {
        this.timetables = timetables;
    }

    public void addTimetable(Timetable timetable) {
        timetables.add(timetable);
        timetable.setCourse(this);
    }

    public void removeTimetable(Timetable timetable) {
        timetables.remove(timetable);
        timetable.setCourse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                ", type=" + type +
                ", professorId=" + (professor != null ? professor.getId() : null) +
                ", tutorCount=" + (tutors != null ? tutors.size() : 0) +
                ", timetableCount=" + (timetables != null ? timetables.size() : 0) +
                '}';
    }
}