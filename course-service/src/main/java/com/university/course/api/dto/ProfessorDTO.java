
package com.university.course.api.dto;

import java.util.List;

public class ProfessorDTO {
    private String id;
    private String name;
    private String department;
    private List<String> courseIds;

    public ProfessorDTO() {
    }

    public ProfessorDTO(String id, String name, String department, List<String> courseIds) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.courseIds = courseIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<String> courseIds) {
        this.courseIds = courseIds;
    }

    @Override
    public String toString() {
        return "ProfessorDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", courseIds=" + courseIds +
                '}';
    }
}