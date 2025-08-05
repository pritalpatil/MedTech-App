package com.medtech.app.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "pharmacies")
public class Pharmacy {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "location", nullable = false)
    private String location;
    
    @Column(name = "contact_number", nullable = true)
    private String contactNumber;
    
    @Column(name = "email", nullable = true)
    private String email;
    
    @Column(name = "address", nullable = true)
    private String address;
    
    @Column(name = "is_24_hours", nullable = true)
    private Boolean is24Hours = false;
    
    // Default constructor
    public Pharmacy() {}
    
    // Constructor with fields
    public Pharmacy(String name, String location, String contactNumber, String email, String address, Boolean is24Hours) {
        this.name = name;
        this.location = location;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
        this.is24Hours = is24Hours;
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
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public Boolean getIs24Hours() {
        return is24Hours;
    }
    
    public void setIs24Hours(Boolean is24Hours) {
        this.is24Hours = is24Hours;
    }
}
