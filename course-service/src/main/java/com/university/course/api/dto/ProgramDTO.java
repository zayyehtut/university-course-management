package com.university.course.api.dto;

import java.util.Set;

public class ProgramDTO {
    private String id;
    private String name;
    private String description;
    private String degreeType;
    private int requiredCredits;
    private Set<String> courseIds;

public ProgramDTO() {
}

public ProgramDTO(String id, String name, String description, String degreeType, int requiredCredits, Set<String> courseIds) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.degreeType = degreeType;
    this.requiredCredits = requiredCredits;
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

public String getDescription() {
    return description;
}

public void setDescription(String description) {
    this.description = description;
}

public String getDegreeType() {
    return degreeType;
}

public void setDegreeType(String degreeType) {
    this.degreeType = degreeType;
}

public int getRequiredCredits() {
    return requiredCredits;
}

public void setRequiredCredits(int requiredCredits) {
    this.requiredCredits = requiredCredits;
}

public Set<String> getCourseIds() {
    return courseIds;
}

public void setCourseIds(Set<String> courseIds) {
    this.courseIds = courseIds;
}
}