package com.university.course.api.dto;

public class UpdateProgramRequest {
    private String name;
    private String description;
    private String degreeType;
    private Integer requiredCredits;

    public UpdateProgramRequest() {
    }

    public UpdateProgramRequest(String name, String description, String degreeType, Integer requiredCredits) {
        this.name = name;
        this.description = description;
        this.degreeType = degreeType;
        this.requiredCredits = requiredCredits;
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

    public Integer getRequiredCredits() {
        return requiredCredits;
    }

    public void setRequiredCredits(Integer requiredCredits) {
        this.requiredCredits = requiredCredits;
    }
    
}
