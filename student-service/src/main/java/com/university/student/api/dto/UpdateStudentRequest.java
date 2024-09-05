package com.university.student.api.dto;

import java.time.LocalDate;

public class UpdateStudentRequest {
    private String name;
    private String email;
    private String type;
    private String address;
    private String phoneNumber;
    private LocalDate dateOfBirth;

    // Constructors
    public UpdateStudentRequest() {}

    public UpdateStudentRequest(String name, String email, String type, String address, String phoneNumber, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.type = type;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    // Getters and Setters
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "UpdateStudentRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", type='" + type + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}