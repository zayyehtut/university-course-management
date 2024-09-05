package com.university.course.api.dto;

public class UpdateProfessorRequest {
    private String name;
    private String department;

    public UpdateProfessorRequest() {
    }

    public UpdateProfessorRequest(String name, String department) {
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
        return "UpdateProfessorRequest{" +
                "name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
