-- MedTech Application Database Schema
-- Database: medtech_db

USE medtech_db;

-- 1. Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 2. Symptoms Table
CREATE TABLE IF NOT EXISTS symptoms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    category VARCHAR(100) NOT NULL, -- respiratory, cardiovascular, neurological, etc.
    severity_level INT CHECK (severity_level >= 1 AND severity_level <= 5)
);

-- 3. Diseases Table
CREATE TABLE IF NOT EXISTS diseases (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    causes TEXT,
    treatments TEXT,
    category VARCHAR(100) NOT NULL, -- infectious, chronic, acute, etc.
    severity_level INT CHECK (severity_level >= 1 AND severity_level <= 5)
);

-- 4. Symptom-Disease Mapping Table (Many-to-Many relationship)
CREATE TABLE IF NOT EXISTS symptom_disease_mapping (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    symptom_id BIGINT NOT NULL,
    disease_id BIGINT NOT NULL,
    confidence_score DECIMAL(3,2) DEFAULT 0.50, -- 0.00 to 1.00
    FOREIGN KEY (symptom_id) REFERENCES symptoms(id) ON DELETE CASCADE,
    FOREIGN KEY (disease_id) REFERENCES diseases(id) ON DELETE CASCADE,
    UNIQUE KEY unique_symptom_disease (symptom_id, disease_id)
);

-- 5. Doctors Table
CREATE TABLE IF NOT EXISTS doctors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    specialty VARCHAR(100) NOT NULL, -- Cardiologist, ENT, General Physician, etc.
    location VARCHAR(255) NOT NULL, -- City/Area
    contact_number VARCHAR(20),
    email VARCHAR(255) UNIQUE,
    experience_years INT,
    consultation_fee DECIMAL(10,2),
    is_available BOOLEAN DEFAULT TRUE
);

-- 6. Pharmacies Table
CREATE TABLE IF NOT EXISTS pharmacies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    contact_number VARCHAR(20),
    email VARCHAR(255),
    address TEXT,
    is_24_hours BOOLEAN DEFAULT FALSE
);

-- 7. Medicines Table
CREATE TABLE IF NOT EXISTS medicines (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    generic_name VARCHAR(255),
    dosage_form VARCHAR(100), -- tablet, syrup, injection, etc.
    strength VARCHAR(50), -- 500mg, 10ml, etc.
    manufacturer VARCHAR(255),
    prescription_required BOOLEAN DEFAULT FALSE
);

-- 8. Pharmacy Inventory Table (Many-to-Many relationship)
CREATE TABLE IF NOT EXISTS pharmacy_inventory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pharmacy_id BIGINT NOT NULL,
    medicine_id BIGINT NOT NULL,
    quantity INT DEFAULT 0,
    price DECIMAL(10,2),
    is_available BOOLEAN DEFAULT TRUE,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (pharmacy_id) REFERENCES pharmacies(id) ON DELETE CASCADE,
    FOREIGN KEY (medicine_id) REFERENCES medicines(id) ON DELETE CASCADE,
    UNIQUE KEY unique_pharmacy_medicine (pharmacy_id, medicine_id)
);

-- 9. User Symptom History Table
CREATE TABLE IF NOT EXISTS user_symptom_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    symptoms TEXT, -- JSON array of symptom IDs
    predicted_diseases TEXT, -- JSON array of disease IDs with confidence scores
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_symptoms_category ON symptoms(category);
CREATE INDEX idx_diseases_category ON diseases(category);
CREATE INDEX idx_doctors_specialty ON doctors(specialty);
CREATE INDEX idx_doctors_location ON doctors(location);
CREATE INDEX idx_pharmacies_location ON pharmacies(location);
CREATE INDEX idx_medicines_name ON medicines(name);
CREATE INDEX idx_pharmacy_inventory_available ON pharmacy_inventory(is_available);

-- Insert sample data for testing

-- Sample Symptoms
INSERT INTO symptoms (name, description, category, severity_level) VALUES
('Fever', 'Elevated body temperature above normal range', 'general', 2),
('Headache', 'Pain in the head or upper neck', 'neurological', 2),
('Cough', 'Sudden expulsion of air from the lungs', 'respiratory', 2),
('Chest Pain', 'Pain or discomfort in the chest area', 'cardiovascular', 4),
('Shortness of Breath', 'Difficulty breathing or breathlessness', 'respiratory', 4),
('Nausea', 'Feeling of sickness with urge to vomit', 'gastrointestinal', 2),
('Fatigue', 'Extreme tiredness and lack of energy', 'general', 1),
('Joint Pain', 'Pain in joints like knees, elbows, wrists', 'musculoskeletal', 3);

-- Sample Diseases
INSERT INTO diseases (name, description, causes, treatments, category, severity_level) VALUES
('Common Cold', 'Viral infection of the upper respiratory tract', 'Rhinovirus, coronavirus', 'Rest, fluids, over-the-counter medications', 'infectious', 1),
('Influenza', 'Viral infection affecting respiratory system', 'Influenza virus', 'Rest, fluids, antiviral medications', 'infectious', 3),
('Hypertension', 'High blood pressure condition', 'Lifestyle factors, genetics, age', 'Lifestyle changes, medications', 'chronic', 3),
('Diabetes Type 2', 'Metabolic disorder affecting blood sugar', 'Obesity, genetics, lifestyle', 'Diet, exercise, medications', 'chronic', 4),
('Pneumonia', 'Infection causing inflammation of air sacs', 'Bacteria, viruses, fungi', 'Antibiotics, rest, hospitalization if severe', 'infectious', 4),
('Migraine', 'Severe recurring headache', 'Genetics, triggers, hormonal changes', 'Pain relievers, preventive medications', 'chronic', 3);

-- Sample Symptom-Disease Mappings
INSERT INTO symptom_disease_mapping (symptom_id, disease_id, confidence_score) VALUES
(1, 1, 0.70), -- Fever -> Common Cold
(1, 2, 0.85), -- Fever -> Influenza
(3, 1, 0.80), -- Cough -> Common Cold
(3, 2, 0.75), -- Cough -> Influenza
(3, 5, 0.60), -- Cough -> Pneumonia
(2, 6, 0.90), -- Headache -> Migraine
(7, 1, 0.50), -- Fatigue -> Common Cold
(7, 2, 0.65), -- Fatigue -> Influenza
(7, 3, 0.40), -- Fatigue -> Hypertension
(7, 4, 0.70); -- Fatigue -> Diabetes

-- Sample Doctors
INSERT INTO doctors (name, specialty, location, contact_number, email, experience_years, consultation_fee) VALUES
('Dr. John Smith', 'Cardiologist', 'New York', '+1-555-0101', 'john.smith@medtech.com', 15, 200.00),
('Dr. Sarah Johnson', 'ENT Specialist', 'Los Angeles', '+1-555-0102', 'sarah.johnson@medtech.com', 12, 180.00),
('Dr. Michael Brown', 'General Physician', 'Chicago', '+1-555-0103', 'michael.brown@medtech.com', 8, 120.00),
('Dr. Emily Davis', 'Neurologist', 'Houston', '+1-555-0104', 'emily.davis@medtech.com', 20, 250.00),
('Dr. Robert Wilson', 'Pulmonologist', 'Phoenix', '+1-555-0105', 'robert.wilson@medtech.com', 18, 220.00);

-- Sample Pharmacies
INSERT INTO pharmacies (name, location, contact_number, email, address, is_24_hours) VALUES
('MedPlus Pharmacy', 'New York', '+1-555-0201', 'info@medplus.com', '123 Main St, New York, NY', TRUE),
('HealthCare Pharmacy', 'Los Angeles', '+1-555-0202', 'info@healthcare.com', '456 Oak Ave, Los Angeles, CA', FALSE),
('QuickMed Pharmacy', 'Chicago', '+1-555-0203', 'info@quickmed.com', '789 Pine St, Chicago, IL', TRUE),
('Family Pharmacy', 'Houston', '+1-555-0204', 'info@family.com', '321 Elm St, Houston, TX', FALSE);

-- Sample Medicines
INSERT INTO medicines (name, generic_name, dosage_form, strength, manufacturer, prescription_required) VALUES
('Paracetamol', 'Acetaminophen', 'tablet', '500mg', 'Generic Pharma', FALSE),
('Ibuprofen', 'Ibuprofen', 'tablet', '400mg', 'Generic Pharma', FALSE),
('Amoxicillin', 'Amoxicillin', 'capsule', '500mg', 'Antibiotic Corp', TRUE),
('Omeprazole', 'Omeprazole', 'capsule', '20mg', 'Digestive Health', TRUE),
('Cetirizine', 'Cetirizine', 'tablet', '10mg', 'Allergy Relief', FALSE);

-- Sample Pharmacy Inventory
INSERT INTO pharmacy_inventory (pharmacy_id, medicine_id, quantity, price, is_available) VALUES
(1, 1, 100, 5.99, TRUE),  -- MedPlus has Paracetamol
(1, 2, 75, 7.99, TRUE),   -- MedPlus has Ibuprofen
(2, 1, 50, 6.49, TRUE),   -- HealthCare has Paracetamol
(2, 3, 25, 15.99, TRUE),  -- HealthCare has Amoxicillin
(3, 1, 200, 5.49, TRUE),  -- QuickMed has Paracetamol
(3, 4, 30, 25.99, TRUE),  -- QuickMed has Omeprazole
(4, 2, 60, 8.99, TRUE),   -- Family has Ibuprofen
(4, 5, 40, 12.99, TRUE);  -- Family has Cetirizine

-- Display table information
SELECT 'Database schema created successfully!' as status; 