package com.medtech.app.controllers;

import com.medtech.app.entities.Medicine;
import com.medtech.app.repositories.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicines")
@CrossOrigin(origins = "*")
public class MedicineController {

    @Autowired
    private MedicineRepository medicineRepository;

    // CREATE - Add a new medicine
    @PostMapping
    public ResponseEntity<Medicine> createMedicine(@RequestBody Medicine medicine) {
        try {
            if (medicine.getName() == null || medicine.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Medicine existingMedicine = medicineRepository.findByName(medicine.getName());
            if (existingMedicine != null) {
                return ResponseEntity.badRequest().build();
            }

            Medicine savedMedicine = medicineRepository.save(medicine);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMedicine);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get all medicines
    @GetMapping
    public ResponseEntity<List<Medicine>> getAllMedicines() {
        try {
            List<Medicine> medicines = medicineRepository.findAll();
            return ResponseEntity.ok(medicines);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get medicine by ID
    @GetMapping("/{id}")
    public ResponseEntity<Medicine> getMedicineById(@PathVariable Long id) {
        try {
            Optional<Medicine> medicine = medicineRepository.findById(id);
            if (medicine.isPresent()) {
                return ResponseEntity.ok(medicine.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get medicine by name
    @GetMapping("/name/{name}")
    public ResponseEntity<Medicine> getMedicineByName(@PathVariable String name) {
        try {
            Medicine medicine = medicineRepository.findByName(name);
            if (medicine != null) {
                return ResponseEntity.ok(medicine);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get medicines by generic name
    @GetMapping("/generic/{genericName}")
    public ResponseEntity<List<Medicine>> getMedicinesByGenericName(@PathVariable String genericName) {
        try {
            List<Medicine> medicines = medicineRepository.findByGenericName(genericName);
            return ResponseEntity.ok(medicines);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get medicines by dosage form
    @GetMapping("/dosage/{dosageForm}")
    public ResponseEntity<List<Medicine>> getMedicinesByDosageForm(@PathVariable String dosageForm) {
        try {
            List<Medicine> medicines = medicineRepository.findByDosageForm(dosageForm);
            return ResponseEntity.ok(medicines);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get over-the-counter medicines (no prescription required)
    @GetMapping("/otc")
    public ResponseEntity<List<Medicine>> getOverTheCounterMedicines() {
        try {
            List<Medicine> medicines = medicineRepository.findByPrescriptionRequiredFalse();
            return ResponseEntity.ok(medicines);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get medicines by manufacturer
    @GetMapping("/manufacturer/{manufacturer}")
    public ResponseEntity<List<Medicine>> getMedicinesByManufacturer(@PathVariable String manufacturer) {
        try {
            List<Medicine> medicines = medicineRepository.findByManufacturer(manufacturer);
            return ResponseEntity.ok(medicines);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // UPDATE - Update medicine by ID
    @PutMapping("/{id}")
    public ResponseEntity<Medicine> updateMedicine(@PathVariable Long id, @RequestBody Medicine medicineDetails) {
        try {
            Optional<Medicine> medicineOptional = medicineRepository.findById(id);
            if (medicineOptional.isPresent()) {
                Medicine existingMedicine = medicineOptional.get();
                
                if (medicineDetails.getName() != null) {
                    existingMedicine.setName(medicineDetails.getName());
                }
                if (medicineDetails.getGenericName() != null) {
                    existingMedicine.setGenericName(medicineDetails.getGenericName());
                }
                if (medicineDetails.getDosageForm() != null) {
                    existingMedicine.setDosageForm(medicineDetails.getDosageForm());
                }
                if (medicineDetails.getStrength() != null) {
                    existingMedicine.setStrength(medicineDetails.getStrength());
                }
                if (medicineDetails.getManufacturer() != null) {
                    existingMedicine.setManufacturer(medicineDetails.getManufacturer());
                }
                if (medicineDetails.getPrescriptionRequired() != null) {
                    existingMedicine.setPrescriptionRequired(medicineDetails.getPrescriptionRequired());
                }
                
                Medicine updatedMedicine = medicineRepository.save(existingMedicine);
                return ResponseEntity.ok(updatedMedicine);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE - Delete medicine by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        try {
            if (medicineRepository.existsById(id)) {
                medicineRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // SEARCH - Find medicines by various criteria
    @GetMapping("/search")
    public ResponseEntity<List<Medicine>> searchMedicines(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String genericName,
            @RequestParam(required = false) String dosageForm,
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) Boolean prescriptionRequired) {
        try {
            List<Medicine> medicines;
            
            if (name != null) {
                medicines = medicineRepository.findByNameContainingIgnoreCase(name);
            } else if (genericName != null) {
                medicines = medicineRepository.findByGenericName(genericName);
            } else if (dosageForm != null) {
                medicines = medicineRepository.findByDosageForm(dosageForm);
            } else if (manufacturer != null) {
                medicines = medicineRepository.findByManufacturer(manufacturer);
            } else if (prescriptionRequired != null) {
                if (prescriptionRequired) {
                    // For prescription required, we need to filter manually since we don't have this method
                    medicines = medicineRepository.findAll().stream()
                        .filter(Medicine::getPrescriptionRequired)
                        .toList();
                } else {
                    medicines = medicineRepository.findByPrescriptionRequiredFalse();
                }
            } else {
                medicines = medicineRepository.findAll();
            }
            
            return ResponseEntity.ok(medicines);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
} 