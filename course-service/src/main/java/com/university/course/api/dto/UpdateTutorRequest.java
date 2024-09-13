package com.university.course.api.dto;

public class UpdateTutorRequest {
    private String name;
    private String email;
    private String specialization;

    // Constructors, getters, and setters
    public UpdateTutorRequest() {
    }

    public UpdateTutorRequest(String name, String email, String specialization) {
        this.name = name;
        this.email = email;
        this.specialization = specialization;
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

    @Override
    public String toString() {
        return "UpdateTutorRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", specialization='" + specialization + '\'' +
                '}';
    }
 
}