package com.medtech.app.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "symptoms")
public class Symptom{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", unique=true)
    private String name;

    @Column(name="description", nullable=true)
    private String description;

    @Column(name="category", nullable=false)
    private String category;

    @Column(name="severity_level", nullable=true)
    private Integer severityLevel;

    public Symptom(){}

    public Symptom(String name, String description, String category, Integer severityLevel) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.severityLevel = severityLevel;
    }

    // Constructor without description and severity (for simple symptoms)
    public Symptom(String name, String category) {
        this.name = name;
        this.category = category;
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
    
    @Override
    public String toString() {
        return "Symptom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", severity_level=" + severityLevel +
                '}';
    }

}
