package com.university.course.api.dto;

import java.util.HashSet;
import java.util.Set;

public class UpdateCourseRequest {
    private String name;
    private Integer credits;
    private String type;
    private String professorId;
    private Set<String> tutorIds;

    public UpdateCourseRequest() {
        this.tutorIds = new HashSet<>();
    }

    public UpdateCourseRequest(String name, Integer credits, String type, String professorId, Set<String> tutorIds) {
        this.name = name;
        this.credits = credits;
        this.type = type;
        this.professorId = professorId;
        this.tutorIds = tutorIds != null ? tutorIds : new HashSet<>();
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

    public Set<String> getTutorIds() {
        return tutorIds;
    }

    public void setTutorIds(Set<String> tutorIds) {
        this.tutorIds = tutorIds;
    }

    @Override
    public String toString() {
        return "UpdateCourseRequest{" +
                "name='" + name + '\'' +
                ", credits=" + credits +
                ", type='" + type + '\'' +
                ", professorId='" + professorId + '\'' +
                ", tutorIds=" + tutorIds +
                '}';
    }
}