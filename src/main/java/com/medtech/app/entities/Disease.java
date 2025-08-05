package com.medtech.app.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "diseases")
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO INCREMENTS THE VALUE
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true, columnDefinition = "TEXT")
    private String description;

    @Column(name = "causes", nullable = true, columnDefinition = "TEXT")
    private String causes;

    @Column(name = "treatments", nullable = true, columnDefinition = "TEXT")
    private String treatments;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "severity_level", nullable = true)
    private Integer severityLevel;

    public Disease() {}

    public Disease(String name, String description, String causes, String treatments, String category, Integer severityLevel){
        this.name = name;
        this.description = description;
        this.causes = causes;
        this.treatments = treatments;
        this.category = category;
        this.severityLevel = severityLevel;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
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
    
    public String getCauses() {
        return causes;
    }
    
    public void setCauses(String causes) {
        this.causes = causes;
    }
    
    public String getTreatments() {
        return treatments;
    }
    
    public void setTreatments(String treatments) {
        this.treatments = treatments;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public Integer getSeverityLevel() {
        return severityLevel;
    }
    
    public void setSeverityLevel(Integer severityLevel) {
        this.severityLevel = severityLevel;
    }
}

    
    
