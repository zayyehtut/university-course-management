package com.university.course.api.dto;

public class CreateCourseRequest {
    private String code;
    private String name;
    private int credits;
    private String type;
    private String professorId;

    public CreateCourseRequest() {
    }

    public CreateCourseRequest(String code, String name, int credits, String type, String professorId) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.type = type;
        this.professorId = professorId;
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
        return "CreateCourseRequest{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                ", type='" + type + '\'' +
                ", professorId='" + professorId + '\'' +
                '}';
    }
}