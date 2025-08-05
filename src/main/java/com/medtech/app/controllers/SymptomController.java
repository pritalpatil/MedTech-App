package com.medtech.app.controllers;

import com.medtech.app.entities.Symptom;
import com.medtech.app.repositories.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * SymptomController - Handles all HTTP requests related to symptoms
 * 
 * This controller provides REST API endpoints for:
 * - Creating new symptoms
 * - Reading symptoms (all, by ID, by category, by severity)
 * - Updating existing symptoms
 * - Deleting symptoms
 * - Searching symptoms
 */
@RestController
@RequestMapping("/api/symptoms")
@CrossOrigin(origins = "*")
public class SymptomController {

    // Dependency Injection - Spring automatically provides SymptomRepository instance
    @Autowired
    private SymptomRepository symptomRepository;

    // ==================== CREATE OPERATIONS ====================

    /**
     * CREATE - Add a new symptom
     * 
     * HTTP Method: POST
     * URL: POST /api/symptoms
     * Request Body: JSON with symptom data
     * Response: 201 Created with the saved symptom, or 400 Bad Request if validation fails
     */
    @PostMapping
    public ResponseEntity<Symptom> createSymptom(@RequestBody Symptom symptom) {
        try {
            // Validate input - check if symptom name is provided
            if (symptom.getName() == null || symptom.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().build(); // 400 Bad Request
            }

            // Check if symptom with same name already exists (assuming names are unique)
            Symptom existingSymptom = symptomRepository.findByName(symptom.getName());
            if (existingSymptom != null) {
                return ResponseEntity.badRequest().build(); // 400 Bad Request - duplicate name
            }

            // Save the symptom to database
            Symptom savedSymptom = symptomRepository.save(symptom);
            
            // Return 201 Created with the saved symptom
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSymptom);
            
        } catch (Exception e) {
            // Log the error (in production, you'd use proper logging)
            System.err.println("Error creating symptom: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    // ==================== READ OPERATIONS ====================

    /**
     * READ - Get all symptoms
     * 
     * HTTP Method: GET
     * URL: GET /api/symptoms
     * Response: 200 OK with list of all symptoms, or 500 Internal Server Error
     */
    @GetMapping
    public ResponseEntity<List<Symptom>> getAllSymptoms() {
        try {
            // Retrieve all symptoms from database
            List<Symptom> symptoms = symptomRepository.findAll();
            
            // Return 200 OK with the list of symptoms
            return ResponseEntity.ok(symptoms);
            
        } catch (Exception e) {
            System.err.println("Error retrieving symptoms: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    /**
     * READ - Get symptom by ID
     * 
     * HTTP Method: GET
     * URL: GET /api/symptoms/{id}
     * Path Variable: id (Long) - the symptom ID
     * Response: 200 OK with symptom, 404 Not Found if not exists, or 500 Internal Server Error
     */
    @GetMapping("/{id}")
    public ResponseEntity<Symptom> getSymptomById(@PathVariable Long id) {
        try {
            // Find symptom by ID - returns Optional<Symptom>
            Optional<Symptom> symptom = symptomRepository.findById(id);
            
            // Check if symptom exists
            if (symptom.isPresent()) {
                return ResponseEntity.ok(symptom.get()); // 200 OK with symptom
            } else {
                return ResponseEntity.notFound().build(); // 404 Not Found
            }
            
        } catch (Exception e) {
            System.err.println("Error retrieving symptom by ID: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    /**
     * READ - Get symptom by name
     * 
     * HTTP Method: GET
     * URL: GET /api/symptoms/name/{name}
     * Path Variable: name (String) - the symptom name
     * Response: 200 OK with symptom, 404 Not Found if not exists, or 500 Internal Server Error
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<Symptom> getSymptomByName(@PathVariable String name) {
        try {
            // Find symptom by name
            Symptom symptom = symptomRepository.findByName(name);
            
            if (symptom != null) {
                return ResponseEntity.ok(symptom); // 200 OK with symptom
            } else {
                return ResponseEntity.notFound().build(); // 404 Not Found
            }
            
        } catch (Exception e) {
            System.err.println("Error retrieving symptom by name: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    /**
     * READ - Get symptoms by category
     * 
     * HTTP Method: GET
     * URL: GET /api/symptoms/category/{category}
     * Path Variable: category (String) - the symptom category
     * Response: 200 OK with list of symptoms, or 500 Internal Server Error
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Symptom>> getSymptomsByCategory(@PathVariable String category) {
        try {
            // Find symptoms by category
            List<Symptom> symptoms = symptomRepository.findByCategory(category);
            
            // Always return 200 OK (even if list is empty)
            return ResponseEntity.ok(symptoms);
            
        } catch (Exception e) {
            System.err.println("Error retrieving symptoms by category: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    /**
     * READ - Get symptoms by severity level
     * 
     * HTTP Method: GET
     * URL: GET /api/symptoms/severity/{severityLevel}
     * Path Variable: severityLevel (Integer) - the severity level
     * Response: 200 OK with list of symptoms, or 500 Internal Server Error
     */
    @GetMapping("/severity/{severityLevel}")
    public ResponseEntity<List<Symptom>> getSymptomsBySeverityLevel(@PathVariable Integer severityLevel) {
        try {
            // Find symptoms by severity level
            List<Symptom> symptoms = symptomRepository.findBySeverityLevel(severityLevel);
            
            return ResponseEntity.ok(symptoms);
            
        } catch (Exception e) {
            System.err.println("Error retrieving symptoms by severity: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    // ==================== UPDATE OPERATIONS ====================

    /**
     * UPDATE - Update symptom by ID
     * 
     * HTTP Method: PUT
     * URL: PUT /api/symptoms/{id}
     * Path Variable: id (Long) - the symptom ID
     * Request Body: JSON with updated symptom data
     * Response: 200 OK with updated symptom, 404 Not Found if not exists, or 500 Internal Server Error
     */
    @PutMapping("/{id}")
    public ResponseEntity<Symptom> updateSymptom(@PathVariable Long id, @RequestBody Symptom symptomDetails) {
        try {
            // Find existing symptom by ID
            Optional<Symptom> symptomOptional = symptomRepository.findById(id);
            
            if (symptomOptional.isPresent()) {
                // Get the existing symptom
                Symptom existingSymptom = symptomOptional.get();
                
                // Update the fields (only update non-null fields)
                if (symptomDetails.getName() != null) {
                    existingSymptom.setName(symptomDetails.getName());
                }
                if (symptomDetails.getDescription() != null) {
                    existingSymptom.setDescription(symptomDetails.getDescription());
                }
                if (symptomDetails.getCategory() != null) {
                    existingSymptom.setCategory(symptomDetails.getCategory());
                }
                if (symptomDetails.getSeverityLevel() != null) {
                    existingSymptom.setSeverityLevel(symptomDetails.getSeverityLevel());
                }
                
                // Save the updated symptom
                Symptom updatedSymptom = symptomRepository.save(existingSymptom);
                
                return ResponseEntity.ok(updatedSymptom); // 200 OK with updated symptom
                
            } else {
                return ResponseEntity.notFound().build(); // 404 Not Found
            }
            
        } catch (Exception e) {
            System.err.println("Error updating symptom: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    // ==================== DELETE OPERATIONS ====================

    /**
     * DELETE - Delete symptom by ID
     * 
     * HTTP Method: DELETE
     * URL: DELETE /api/symptoms/{id}
     * Path Variable: id (Long) - the symptom ID
     * Response: 204 No Content if deleted, 404 Not Found if not exists, or 500 Internal Server Error
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSymptom(@PathVariable Long id) {
        try {
            // Check if symptom exists before deleting
            if (symptomRepository.existsById(id)) {
                // Delete the symptom
                symptomRepository.deleteById(id);
                return ResponseEntity.noContent().build(); // 204 No Content
            } else {
                return ResponseEntity.notFound().build(); // 404 Not Found
            }
            
        } catch (Exception e) {
            System.err.println("Error deleting symptom: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    // ==================== SEARCH OPERATIONS ====================

    /**
     * SEARCH - Find symptoms by category and severity level
     * 
     * HTTP Method: GET
     * URL: GET /api/symptoms/search?category={category}&severityLevel={severityLevel}
     * Query Parameters: category (String), severityLevel (Integer)
     * Response: 200 OK with list of symptoms, or 500 Internal Server Error
     */
    @GetMapping("/search")
    public ResponseEntity<List<Symptom>> searchSymptoms(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer severityLevel) {
        try {
            List<Symptom> symptoms;
            
            // Different search logic based on provided parameters
            if (category != null && severityLevel != null) {
                // Search by both category and severity level
                symptoms = symptomRepository.findByCategoryAndSeverityLevel(category, severityLevel);
            } else if (category != null) {
                // Search by category only
                symptoms = symptomRepository.findByCategory(category);
            } else if (severityLevel != null) {
                // Search by severity level only
                symptoms = symptomRepository.findBySeverityLevel(severityLevel);
            } else {
                // No parameters provided, return all symptoms
                symptoms = symptomRepository.findAll();
            }
            
            return ResponseEntity.ok(symptoms);
            
        } catch (Exception e) {
            System.err.println("Error searching symptoms: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }
} 