# MedTech Application

A comprehensive healthcare application built with Spring Boot and React, providing symptom-based disease prediction, doctor search, and medicine availability tracking.

## 🏥 Features

### 1. Symptom-Based Disease Prediction
- Input symptoms and get probable diseases
- View disease information (causes, treatments, severity)
- Categorized disease information

### 2. Doctor Search & Booking
- Search doctors by specialty (ENT, General Physician, etc.)
- Filter by location and availability
- View doctor details (experience, consultation fees, contact info)

### 3. Medicine Availability Tracker
- Search medicines by name, generic name, or manufacturer
- Check prescription requirements
- Find nearby pharmacies with medicine availability

## 🛠️ Tech Stack

### Backend
- **Java 17+**
- **Spring Boot 3.5.4**
- **Spring Data JPA**
- **MySQL Database**
- **Maven**

### Frontend (Coming Soon - Day 3)
- **React 18**
- **Node.js**
- **npm**

## 📁 Project Structure

```
medtech-app/
├── src/
│   ├── main/
│   │   ├── java/com/medtech/app/
│   │   │   ├── controllers/     # REST API Controllers
│   │   │   ├── entities/        # JPA Entity Classes
│   │   │   ├── repositories/    # Data Access Layer
│   │   │   └── MedtechApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
├── README.md
└── .gitignore
```

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6+

### Database Setup
1. Create MySQL database:
```sql
CREATE DATABASE medtech_db;
```

2. Update `application.properties` with your database credentials:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Running the Application
1. Clone the repository:
```bash
git clone <your-repo-url>
cd medtech-app
```

2. Run the application:
```bash
mvn spring-boot:run
```

3. Access the application:
- Application: http://localhost:8080
- Test endpoint: http://localhost:8080/api/test/hello

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Available Endpoints

#### Users
- `GET /users` - Get all users
- `GET /users/{id}` - Get user by ID
- `GET /users/email/{email}` - Get user by email
- `POST /users` - Create new user
- `PUT /users/{id}` - Update user
- `DELETE /users/{id}` - Delete user

#### Symptoms
- `GET /symptoms` - Get all symptoms
- `GET /symptoms/{id}` - Get symptom by ID
- `GET /symptoms/category/{category}` - Get symptoms by category
- `GET /symptoms/severity/{severityLevel}` - Get symptoms by severity
- `POST /symptoms` - Create new symptom
- `PUT /symptoms/{id}` - Update symptom
- `DELETE /symptoms/{id}` - Delete symptom
- `GET /symptoms/search?category={category}&severityLevel={level}` - Search symptoms

#### Diseases
- `GET /diseases` - Get all diseases
- `GET /diseases/{id}` - Get disease by ID
- `GET /diseases/category/{category}` - Get diseases by category
- `GET /diseases/severity/{severityLevel}` - Get diseases by severity
- `POST /diseases` - Create new disease
- `PUT /diseases/{id}` - Update disease
- `DELETE /diseases/{id}` - Delete disease
- `GET /diseases/search?category={category}&severityLevel={level}` - Search diseases

#### Doctors
- `GET /doctors` - Get all doctors
- `GET /doctors/{id}` - Get doctor by ID
- `GET /doctors/specialty/{specialty}` - Get doctors by specialty
- `GET /doctors/location/{location}` - Get doctors by location
- `GET /doctors/available` - Get available doctors
- `GET /doctors/available/specialty/{specialty}` - Get available doctors by specialty
- `POST /doctors` - Create new doctor
- `PUT /doctors/{id}` - Update doctor
- `DELETE /doctors/{id}` - Delete doctor
- `GET /doctors/search?specialty={specialty}&location={location}&isAvailable={boolean}` - Search doctors

#### Medicines
- `GET /medicines` - Get all medicines
- `GET /medicines/{id}` - Get medicine by ID
- `GET /medicines/generic/{genericName}` - Get medicines by generic name
- `GET /medicines/dosage/{dosageForm}` - Get medicines by dosage form
- `GET /medicines/otc` - Get over-the-counter medicines
- `GET /medicines/manufacturer/{manufacturer}` - Get medicines by manufacturer
- `POST /medicines` - Create new medicine
- `PUT /medicines/{id}` - Update medicine
- `DELETE /medicines/{id}` - Delete medicine
- `GET /medicines/search?name={name}&genericName={generic}&dosageForm={form}&manufacturer={manufacturer}&prescriptionRequired={boolean}` - Search medicines

#### Pharmacies
- `GET /pharmacies` - Get all pharmacies
- `GET /pharmacies/{id}` - Get pharmacy by ID
- `GET /pharmacies/location/{location}` - Get pharmacies by location
- `GET /pharmacies/24hours` - Get 24-hour pharmacies
- `GET /pharmacies/24hours/location/{location}` - Get 24-hour pharmacies by location
- `POST /pharmacies` - Create new pharmacy
- `PUT /pharmacies/{id}` - Update pharmacy
- `DELETE /pharmacies/{id}` - Delete pharmacy
- `GET /pharmacies/search?location={location}&is24Hours={boolean}` - Search pharmacies

## 🧪 Testing the API

### Using curl

1. **Create a user:**
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","password":"password123"}'
```

2. **Get all users:**
```bash
curl http://localhost:8080/api/users
```

3. **Create a symptom:**
```bash
curl -X POST http://localhost:8080/api/symptoms \
  -H "Content-Type: application/json" \
  -d '{"name":"Fever","description":"Elevated body temperature","category":"General","severityLevel":3}'
```

4. **Search doctors by specialty:**
```bash
curl "http://localhost:8080/api/doctors/search?specialty=General%20Physician"
```

### Using Postman
1. Import the collection (if available)
2. Set base URL: `http://localhost:8080/api`
3. Test individual endpoints

## 📊 Database Schema

### Tables
- `users` - User information
- `symptoms` - Medical symptoms
- `diseases` - Disease information
- `doctors` - Doctor profiles
- `medicines` - Medicine catalog
- `pharmacies` - Pharmacy information
- `symptom_disease_mapping` - Symptom-disease relationships
- `pharmacy_inventory` - Medicine availability in pharmacies
- `user_symptom_history` - User symptom tracking

## 🔧 Configuration

### Application Properties
Key configuration in `application.properties`:
```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/medtech_db
spring.datasource.username=root
spring.datasource.password=

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## 📈 Development Progress

### ✅ Completed (Day 1-2)
- [x] Spring Boot project setup
- [x] Database design and schema
- [x] Entity classes with JPA annotations
- [x] Repository interfaces with custom methods
- [x] REST API controllers with full CRUD operations
- [x] API testing and validation
- [x] Sample data insertion

### 🚧 In Progress (Day 3)
- [ ] React frontend setup
- [ ] Component development
- [ ] API integration
- [ ] User interface design

### 📋 Planned
- [ ] User authentication
- [ ] Advanced search features
- [ ] Real-time notifications
- [ ] Mobile responsiveness
- [ ] Deployment configuration

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📝 License

This project is for educational purposes.

## 👨‍💻 Author

Created as a learning project for full-stack development with Spring Boot and React.

## 📞 Support

For questions or issues, please create an issue in the GitHub repository.

---

**Note:** This is a learning project. For production use, additional security, validation, and error handling should be implemented. 