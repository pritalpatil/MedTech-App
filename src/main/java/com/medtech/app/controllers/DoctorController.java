package com.medtech.app.controllers;

import com.medtech.app.entities.Doctor;
import com.medtech.app.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    // CREATE - Add a new doctor
    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        try {
            if (doctor.getName() == null || doctor.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            if (doctor.getEmail() != null && doctorRepository.findByEmail(doctor.getEmail()) != null) {
                return ResponseEntity.badRequest().build();
            }

            Doctor savedDoctor = doctorRepository.save(doctor);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get all doctors
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        try {
            List<Doctor> doctors = doctorRepository.findAll();
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get doctor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        try {
            Optional<Doctor> doctor = doctorRepository.findById(id);
            if (doctor.isPresent()) {
                return ResponseEntity.ok(doctor.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get doctors by specialty
    @GetMapping("/specialty/{specialty}")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialty(@PathVariable String specialty) {
        try {
            List<Doctor> doctors = doctorRepository.findBySpecialty(specialty);
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get doctors by location
    @GetMapping("/location/{location}")
    public ResponseEntity<List<Doctor>> getDoctorsByLocation(@PathVariable String location) {
        try {
            List<Doctor> doctors = doctorRepository.findByLocation(location);
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get available doctors
    @GetMapping("/available")
    public ResponseEntity<List<Doctor>> getAvailableDoctors() {
        try {
            List<Doctor> doctors = doctorRepository.findByIsAvailableTrue();
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // READ - Get available doctors by specialty
    @GetMapping("/available/specialty/{specialty}")
    public ResponseEntity<List<Doctor>> getAvailableDoctorsBySpecialty(@PathVariable String specialty) {
        try {
            List<Doctor> doctors = doctorRepository.findBySpecialtyAndIsAvailableTrue(specialty);
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // UPDATE - Update doctor by ID
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctorDetails) {
        try {
            Optional<Doctor> doctorOptional = doctorRepository.findById(id);
            if (doctorOptional.isPresent()) {
                Doctor existingDoctor = doctorOptional.get();
                
                if (doctorDetails.getName() != null) {
                    existingDoctor.setName(doctorDetails.getName());
                }
                if (doctorDetails.getSpecialty() != null) {
                    existingDoctor.setSpecialty(doctorDetails.getSpecialty());
                }
                if (doctorDetails.getLocation() != null) {
                    existingDoctor.setLocation(doctorDetails.getLocation());
                }
                if (doctorDetails.getContactNumber() != null) {
                    existingDoctor.setContactNumber(doctorDetails.getContactNumber());
                }
                if (doctorDetails.getEmail() != null) {
                    existingDoctor.setEmail(doctorDetails.getEmail());
                }
                if (doctorDetails.getExperienceYears() != null) {
                    existingDoctor.setExperienceYears(doctorDetails.getExperienceYears());
                }
                if (doctorDetails.getConsultationFee() != null) {
                    existingDoctor.setConsultationFee(doctorDetails.getConsultationFee());
                }
                if (doctorDetails.getIsAvailable() != null) {
                    existingDoctor.setIsAvailable(doctorDetails.getIsAvailable());
                }
                
                Doctor updatedDoctor = doctorRepository.save(existingDoctor);
                return ResponseEntity.ok(updatedDoctor);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE - Delete doctor by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        try {
            if (doctorRepository.existsById(id)) {
                doctorRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // SEARCH - Find doctors by specialty and location
    @GetMapping("/search")
    public ResponseEntity<List<Doctor>> searchDoctors(
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Boolean isAvailable) {
        try {
            List<Doctor> doctors;
            
            if (specialty != null && location != null) {
                doctors = doctorRepository.findBySpecialtyAndLocation(specialty, location);
            } else if (specialty != null) {
                doctors = doctorRepository.findBySpecialty(specialty);
            } else if (location != null) {
                doctors = doctorRepository.findByLocation(location);
            } else {
                doctors = doctorRepository.findAll();
            }
            
            // Filter by availability if specified
            if (isAvailable != null && isAvailable) {
                doctors = doctors.stream()
                    .filter(Doctor::getIsAvailable)
                    .toList();
            }
            
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
} 