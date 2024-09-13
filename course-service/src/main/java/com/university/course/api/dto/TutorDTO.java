// File: src/main/java/com/university/course/api/dto/TutorDTO.java
package com.university.course.api.dto;

import java.util.HashSet;
import java.util.Set;

public class TutorDTO {
    private String id;
    private String name;
    private String email;
    private String specialization;
    private Set<String> courseIds;

    public TutorDTO() {
        this.courseIds = new HashSet<>();
    }

    public TutorDTO(String id, String name, String email, String specialization, Set<String> courseIds) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.specialization = specialization;
        this.courseIds = courseIds;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Set<String> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(Set<String> courseIds) {
        this.courseIds = courseIds;
    }

    @Override
    public String toString() {
        return "TutorDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", specialization='" + specialization + '\'' +
                ", courseIds=" + courseIds +
                '}';
    }
}