package com.medtech.app.controllers;

import com.medtech.app.entities.Pharmacy;
import com.medtech.app.repositories.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pharmacies")
@CrossOrigin(origins = "*")
public class PharmacyController {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    // CREATE - Add a new pharmacy
    @PostMapping
    public ResponseEntity<Pharmacy> createPharmacy(@RequestBody Pharmacy pharmacy) {
        try {
            if (pharmacy.getName() == null || pharmacy.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Pharmacy existingPharmacy = pharmacyRepository.findByName(pharmacy.getName());
            if (existingPharmacy != null) {
                return ResponseEntity.badRequest().build();
            }

            Pharmacy savedPharmacy = pharmacyRepository.save(pharmacy);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPharmacy);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get all pharmacies
    @GetMapping
    public ResponseEntity<List<Pharmacy>> getAllPharmacies() {
        try {
            List<Pharmacy> pharmacies = pharmacyRepository.findAll();
            return ResponseEntity.ok(pharmacies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get pharmacy by ID
    @GetMapping("/{id}")
    public ResponseEntity<Pharmacy> getPharmacyById(@PathVariable Long id) {
        try {
            Optional<Pharmacy> pharmacy = pharmacyRepository.findById(id);
            if (pharmacy.isPresent()) {
                return ResponseEntity.ok(pharmacy.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get pharmacy by name
    @GetMapping("/name/{name}")
    public ResponseEntity<Pharmacy> getPharmacyByName(@PathVariable String name) {
        try {
            Pharmacy pharmacy = pharmacyRepository.findByName(name);
            if (pharmacy != null) {
                return ResponseEntity.ok(pharmacy);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get pharmacies by location
    @GetMapping("/location/{location}")
    public ResponseEntity<List<Pharmacy>> getPharmaciesByLocation(@PathVariable String location) {
        try {
            List<Pharmacy> pharmacies = pharmacyRepository.findByLocation(location);
            return ResponseEntity.ok(pharmacies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get 24-hour pharmacies
    @GetMapping("/24hours")
    public ResponseEntity<List<Pharmacy>> get24HourPharmacies() {
        try {
            List<Pharmacy> pharmacies = pharmacyRepository.findByIs24HoursTrue();
            return ResponseEntity.ok(pharmacies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get 24-hour pharmacies by location
    @GetMapping("/24hours/location/{location}")
    public ResponseEntity<List<Pharmacy>> get24HourPharmaciesByLocation(@PathVariable String location) {
        try {
            List<Pharmacy> pharmacies = pharmacyRepository.findByLocationAndIs24HoursTrue(location);
            return ResponseEntity.ok(pharmacies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // UPDATE - Update pharmacy by ID
    @PutMapping("/{id}")
    public ResponseEntity<Pharmacy> updatePharmacy(@PathVariable Long id, @RequestBody Pharmacy pharmacyDetails) {
        try {
            Optional<Pharmacy> pharmacyOptional = pharmacyRepository.findById(id);
            if (pharmacyOptional.isPresent()) {
                Pharmacy existingPharmacy = pharmacyOptional.get();
                
                if (pharmacyDetails.getName() != null) {
                    existingPharmacy.setName(pharmacyDetails.getName());
                }
                if (pharmacyDetails.getLocation() != null) {
                    existingPharmacy.setLocation(pharmacyDetails.getLocation());
                }
                if (pharmacyDetails.getContactNumber() != null) {
                    existingPharmacy.setContactNumber(pharmacyDetails.getContactNumber());
                }
                if (pharmacyDetails.getEmail() != null) {
                    existingPharmacy.setEmail(pharmacyDetails.getEmail());
                }
                if (pharmacyDetails.getAddress() != null) {
                    existingPharmacy.setAddress(pharmacyDetails.getAddress());
                }
                if (pharmacyDetails.getIs24Hours() != null) {
                    existingPharmacy.setIs24Hours(pharmacyDetails.getIs24Hours());
                }
                
                Pharmacy updatedPharmacy = pharmacyRepository.save(existingPharmacy);
                return ResponseEntity.ok(updatedPharmacy);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE - Delete pharmacy by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePharmacy(@PathVariable Long id) {
        try {
            if (pharmacyRepository.existsById(id)) {
                pharmacyRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // SEARCH - Find pharmacies by location and 24-hour availability
    @GetMapping("/search")
    public ResponseEntity<List<Pharmacy>> searchPharmacies(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Boolean is24Hours) {
        try {
            List<Pharmacy> pharmacies;
            
            if (location != null && is24Hours != null && is24Hours) {
                pharmacies = pharmacyRepository.findByLocationAndIs24HoursTrue(location);
            } else if (location != null) {
                pharmacies = pharmacyRepository.findByLocation(location);
            } else if (is24Hours != null && is24Hours) {
                pharmacies = pharmacyRepository.findByIs24HoursTrue();
            } else {
                pharmacies = pharmacyRepository.findAll();
            }
            
            return ResponseEntity.ok(pharmacies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
} 