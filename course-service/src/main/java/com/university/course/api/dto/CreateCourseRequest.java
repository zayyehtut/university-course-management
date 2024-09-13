package com.university.course.api.dto;

import java.util.HashSet;
import java.util.Set;


public class CreateCourseRequest {
    private String code;
    private String name;
    private int credits;
    private String type;
    private String professorId;
    private Set<String> tutorIds;
    private Set<String> programIds;

    

    public CreateCourseRequest() {
        this.tutorIds = new HashSet<>();
        this.programIds = new HashSet<>();

    }

    public CreateCourseRequest(String code, String name, int credits, String type, String professorId, Set<String> tutorIds, Set<String> programIds) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.type = type;
        this.professorId = professorId;
        this.tutorIds = tutorIds != null ? tutorIds : new HashSet<>();
        this.programIds = programIds != null ? programIds : new HashSet<>();
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

    public Set<String> getTutorIds() {
        return tutorIds;
    }

    public void setTutorIds(Set<String> tutorIds) {
        this.tutorIds = tutorIds;
    }

    
    public Set<String> getProgramIds() {
        return programIds;
    }

    public void setProgramIds(Set<String> programIds) {
        this.programIds = programIds;
    }
     
    @Override
    public String toString() {
        return "CreateCourseRequest{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                ", type='" + type + '\'' +
                ", professorId='" + professorId + '\'' +
                ", tutorIds=" + tutorIds +
                ", programIds=" + programIds +
                '}';
    }
}