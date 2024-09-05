package com.university.course.api.dto;

public class UpdateCourseRequest {
    private String name;
    private Integer credits;
    private String type;
    private String professorId;

    public UpdateCourseRequest() {
        // Default constructor
    }

    public UpdateCourseRequest(String name, Integer credits, String type, String professorId) {
        this.name = name;
        this.credits = credits;
        this.type = type;
        this.professorId = professorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }

    @Override
    public String toString() {
        return "UpdateCourseRequest{" +
                "name='" + name + '\'' +
                ", credits=" + credits +
                ", type='" + type + '\'' +
                ", professorId='" + professorId + '\'' +
                '}';
    }
}