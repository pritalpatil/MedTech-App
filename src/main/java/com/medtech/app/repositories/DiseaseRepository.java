package com.medtech.app.repositories;

import com.medtech.app.entities.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    
    // Basic searches
    Disease findByName(String name);
    List<Disease> findByCategory(String category);
    List<Disease> findBySeverityLevel(Integer severityLevel);
    
    // Combined searches
    List<Disease> findByCategoryAndSeverityLevel(String category, Integer severityLevel);
    
    // Range searches
    List<Disease> findBySeverityLevelLessThanEqual(Integer severityLevel);
    List<Disease> findBySeverityLevelGreaterThan(Integer severityLevel);
    
    // Pattern searches
    List<Disease> findByNameContainingIgnoreCase(String name);
    
    // Multiple values
    List<Disease> findByCategoryIn(List<String> categories);
} 