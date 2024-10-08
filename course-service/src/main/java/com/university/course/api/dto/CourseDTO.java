package com.university.course.api.dto;
import java.util.HashSet;
import java.util.Set;

public class CourseDTO {
    private String id;
    private String code;
    private String name;
    private int credits;
    private String type;
    private String professorId;
    private String professorName;
    private Set<TutorDTO> tutors;
    private Set<TimetableDTO> timetables;
    private Set<String> programIds;


    public CourseDTO() {
        // Default constructor
        this.tutors = new HashSet<>();
        this.timetables = new HashSet<>();
    }

    public CourseDTO(String id, String code, String name, int credits, String type, String professorId, String professorName) {
  
        this.id = id;
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.type = type;
        this.professorId = professorId;
        this.professorName = professorName;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    
    public Set<TutorDTO> getTutors() {
        return tutors;
    }

    public void setTutors(Set<TutorDTO> tutors) {
        this.tutors = tutors;
    }


   
    public Set<TimetableDTO> getTimetables() {
        return timetables;
    }
    

    public void setTimetables(Set<TimetableDTO> timetables) {
        this.timetables = timetables;
    }

    public Set<String> getProgramIds() {
        return programIds;
    }

    public void setProgramIds(Set<String> programIds) {
        this.programIds = programIds;
    }
    
    @Override
    public String toString() {
        return "CourseDTO{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", programIds=" + programIds +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                ", type='" + type + '\'' +
                ", professorId='" + professorId + '\'' +
                ", professorName='" + professorName + '\'' +
                ", tutors=" + tutors +
                ", timetables=" + timetables +
                '}';
    }

}