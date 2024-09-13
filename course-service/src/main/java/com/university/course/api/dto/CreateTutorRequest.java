package com.university.course.api.dto;

public class CreateTutorRequest {
    private String name;
    private String email;
    private String specialization;

    // Constructors, getters, and setters
    public CreateTutorRequest() {
    }

    public CreateTutorRequest(String name, String email, String specialization) {
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
    // toString method
    @Override
    public String toString() {
        return "CreateTutorRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}