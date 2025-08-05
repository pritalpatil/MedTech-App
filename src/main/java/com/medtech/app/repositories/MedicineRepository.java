package com.medtech.app.repositories;

import java.util.List;
import com.medtech.app.entities.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    // Single object - medicine names are unique
    Medicine findByName(String name);

    // List - multiple medicines can have same generic name
    List<Medicine> findByGenericName(String genericName);

    // List - multiple medicines can have same dosage form
    List<Medicine> findByDosageForm(String dosageForm);
    
    // List - multiple medicines don't require prescription
    List<Medicine> findByPrescriptionRequiredFalse();
    
    // List - multiple medicines from same manufacturer
    List<Medicine> findByManufacturer(String manufacturer);
    
    // List - pattern search returns multiple results
    List<Medicine> findByNameContainingIgnoreCase(String name);

}