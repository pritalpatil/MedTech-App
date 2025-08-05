package com.medtech.app.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "specialty", nullable = false)
    private String specialty;
    
    @Column(name = "location", nullable = false)
    private String location;
    
    @Column(name = "contact_number", nullable = true)
    private String contactNumber;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "experience_years", nullable = true)
    private Integer experienceYears;
    
    @Column(name = "consultation_fee", nullable = true)
    private Double consultationFee;
    
    @Column(name = "is_available", nullable = true)
    private Boolean isAvailable = true;
    
    // Default constructor
    public Doctor() {}
    
    // Constructor with fields
    public Doctor(String name, String specialty, String location, String contactNumber, String email, Integer experienceYears, Double consultationFee) {
        this.name = name;
        this.specialty = specialty;
        this.location = location;
        this.contactNumber = contactNumber;
        this.email = email;
        this.experienceYears = experienceYears;
        this.consultationFee = consultationFee;
        this.isAvailable = isAvailable;
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
    
    public String getSpecialty() {
        return specialty;
    }
    
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getContactNumber() {
        return contactNumber;
    }
    
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Integer getExperienceYears() {
        return experienceYears;
    }
    
    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }
    
    public Double getConsultationFee() {
        return consultationFee;
    }
    
    public void setConsultationFee(Double consultationFee) {
        this.consultationFee = consultationFee;
    }
    
    public Boolean getIsAvailable() {
        return isAvailable;
    }
    
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}

