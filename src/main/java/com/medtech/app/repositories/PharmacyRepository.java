package com.medtech.app.repositories;

import com.medtech.app.entities.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
    
    // Find pharmacies by location
    List<Pharmacy> findByLocation(String location);
    
    // Find 24-hour pharmacies
    List<Pharmacy> findByIs24HoursTrue();
    
    // Find pharmacies by location and 24-hour availability
    List<Pharmacy> findByLocationAndIs24HoursTrue(String location);
    
    // Find pharmacy by name
    Pharmacy findByName(String name);
} 