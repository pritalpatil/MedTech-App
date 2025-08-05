package com.medtech.app.repositories;

import com.medtech.app.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    
    // Find doctors by specialty
    List<Doctor> findBySpecialty(String specialty);
    
    // Find doctors by location
    List<Doctor> findByLocation(String location);
    
    // Find doctors by specialty and location
    List<Doctor> findBySpecialtyAndLocation(String specialty, String location);
    
    // Find available doctors
    List<Doctor> findByIsAvailableTrue();
    
    // Find available doctors by specialty
    List<Doctor> findBySpecialtyAndIsAvailableTrue(String specialty);
    
    // Find doctors by experience years
    List<Doctor> findByExperienceYearsGreaterThanEqual(Integer experienceYears);
    
    // Find doctors by consultation fee range
    List<Doctor> findByConsultationFeeLessThanEqual(Double maxFee);
    
    // Find doctor by email
    Doctor findByEmail(String email);
} 