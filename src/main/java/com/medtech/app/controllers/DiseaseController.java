package com.medtech.app.controllers;

import com.medtech.app.entities.Disease;
import com.medtech.app.repositories.DiseaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/diseases")
@CrossOrigin(origins = "*")
public class DiseaseController {

    @Autowired
    private DiseaseRepository diseaseRepository;

    // CREATE - Add a new disease
    @PostMapping
    public ResponseEntity<Disease> createDisease(@RequestBody Disease disease) {
        try {
            if (disease.getName() == null || disease.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Disease existingDisease = diseaseRepository.findByName(disease.getName());
            if (existingDisease != null) {
                return ResponseEntity.badRequest().build();
            }

            Disease savedDisease = diseaseRepository.save(disease);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDisease);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get all diseases
    @GetMapping
    public ResponseEntity<List<Disease>> getAllDiseases() {
        try {
            List<Disease> diseases = diseaseRepository.findAll();
            return ResponseEntity.ok(diseases);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get disease by ID
    @GetMapping("/{id}")
    public ResponseEntity<Disease> getDiseaseById(@PathVariable Long id) {
        try {
            Optional<Disease> disease = diseaseRepository.findById(id);
            if (disease.isPresent()) {
                return ResponseEntity.ok(disease.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get disease by name
    @GetMapping("/name/{name}")
    public ResponseEntity<Disease> getDiseaseByName(@PathVariable String name) {
        try {
            Disease disease = diseaseRepository.findByName(name);
            if (disease != null) {
                return ResponseEntity.ok(disease);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get diseases by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Disease>> getDiseasesByCategory(@PathVariable String category) {
        try {
            List<Disease> diseases = diseaseRepository.findByCategory(category);
            return ResponseEntity.ok(diseases);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get diseases by severity level
    @GetMapping("/severity/{severityLevel}")
    public ResponseEntity<List<Disease>> getDiseasesBySeverityLevel(@PathVariable Integer severityLevel) {
        try {
            List<Disease> diseases = diseaseRepository.findBySeverityLevel(severityLevel);
            return ResponseEntity.ok(diseases);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // UPDATE - Update disease by ID
    @PutMapping("/{id}")
    public ResponseEntity<Disease> updateDisease(@PathVariable Long id, @RequestBody Disease diseaseDetails) {
        try {
            Optional<Disease> diseaseOptional = diseaseRepository.findById(id);
            if (diseaseOptional.isPresent()) {
                Disease existingDisease = diseaseOptional.get();
                
                if (diseaseDetails.getName() != null) {
                    existingDisease.setName(diseaseDetails.getName());
                }
                if (diseaseDetails.getDescription() != null) {
                    existingDisease.setDescription(diseaseDetails.getDescription());
                }
                if (diseaseDetails.getCauses() != null) {
                    existingDisease.setCauses(diseaseDetails.getCauses());
                }
                if (diseaseDetails.getTreatments() != null) {
                    existingDisease.setTreatments(diseaseDetails.getTreatments());
                }
                if (diseaseDetails.getCategory() != null) {
                    existingDisease.setCategory(diseaseDetails.getCategory());
                }
                if (diseaseDetails.getSeverityLevel() != null) {
                    existingDisease.setSeverityLevel(diseaseDetails.getSeverityLevel());
                }
                
                Disease updatedDisease = diseaseRepository.save(existingDisease);
                return ResponseEntity.ok(updatedDisease);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE - Delete disease by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisease(@PathVariable Long id) {
        try {
            if (diseaseRepository.existsById(id)) {
                diseaseRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // SEARCH - Find diseases by category and severity level
    @GetMapping("/search")
    public ResponseEntity<List<Disease>> searchDiseases(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer severityLevel) {
        try {
            List<Disease> diseases;
            
            if (category != null && severityLevel != null) {
                diseases = diseaseRepository.findByCategoryAndSeverityLevel(category, severityLevel);
            } else if (category != null) {
                diseases = diseaseRepository.findByCategory(category);
            } else if (severityLevel != null) {
                diseases = diseaseRepository.findBySeverityLevel(severityLevel);
            } else {
                diseases = diseaseRepository.findAll();
            }
            
            return ResponseEntity.ok(diseases);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
} 