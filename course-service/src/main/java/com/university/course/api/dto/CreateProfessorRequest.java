package com.university.course.api.dto;

public class CreateProfessorRequest {
    private String name;
    private String department;

    public CreateProfessorRequest() {
    }

    public CreateProfessorRequest(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "CreateProfessorRequest{" +
                "name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}