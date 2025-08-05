package com.medtech.app.repositories;

import com.medtech.app.entities.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Long> {

    Symptom findByName(String name);

    List<Symptom> findByCategory(String category);
    
    List<Symptom> findBySeverityLevel(Integer severityLevel);
    
    // Combined searches
    List<Symptom> findByCategoryAndSeverityLevel(String category, Integer severityLevel);

}