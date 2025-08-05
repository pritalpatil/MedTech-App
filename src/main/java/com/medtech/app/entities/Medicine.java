package com.medtech.app.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "medicines")
public class Medicine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "generic_name", nullable = true)
    private String genericName;
    
    @Column(name = "dosage_form", nullable = true)
    private String dosageForm;
    
    @Column(name = "strength", nullable = true)
    private String strength;
    
    @Column(name = "manufacturer", nullable = true)
    private String manufacturer;
    
    @Column(name = "prescription_required", nullable = true)
    private Boolean prescriptionRequired = false;
    
    // Default constructor
    public Medicine() {}
    
    // Constructor with fields
    public Medicine(String name, String genericName, String dosageForm, String strength, String manufacturer, Boolean prescriptionRequired) {
        this.name = name;
        this.genericName = genericName;
        this.dosageForm = dosageForm;
        this.strength = strength;
        this.manufacturer = manufacturer;
        this.prescriptionRequired = prescriptionRequired;
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
    
    public String getGenericName() {
        return genericName;
    }
    
    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }
    
    public String getDosageForm() {
        return dosageForm;
    }
    
    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }
    
    public String getStrength() {
        return strength;
    }
    
    public void setStrength(String strength) {
        this.strength = strength;
    }
    
    public String getManufacturer() {
        return manufacturer;
    }
    
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    
    public Boolean getPrescriptionRequired() {
        return prescriptionRequired;
    }
    
    public void setPrescriptionRequired(Boolean prescriptionRequired) {
        this.prescriptionRequired = prescriptionRequired;
    }
}
