# University Course Management System

This project is a microservices-based implementation of a University Course Management System. It consists of four main components: Student Service, Course Service, Enrollment Service, and API Gateway.

## Project Structure

Each service follows a layered architecture:

- `api`: Controllers for handling HTTP requests and Data Transfer Objects (DTOs)
- `service`: Business logic implementation
- `domain`: Domain models and repository interfaces
- `exception`: Custom exception classes for handling errors and providing meaningful feedback

```
service-name/
├── api/
│   └── controllers/
│   └── dto/
├── service/
│   └── impl/
├── domain/
│   ├── model/
│   └── repository/
└── exception/
```

- `student-service`: Manages student information
- `course-service`: Handles course, professor, tutor, and program data
- `enrollment-service`: Manages student enrollments and grades
- `common`: Shared utilities and models
- `api-gateway`: Routes requests to appropriate services

## Technologies Used

- Java 21
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database (in-memory for development/testing)
- Maven 3.8+
- Spring Cloud Gateway

## Prerequisites

- JDK 21
- Maven 3.8+

## Service Ports

- API Gateway: 8080
- Student Service: 8081
- Enrollment Service: 8082
- Course Service: 8083

**Note:** Please ensure that these ports are available on your system before running the services. If you need to change a port, update the `server.port` property in the respective `application.properties` file.

## Building the Project

To build all services, run the following command in the root directory:

```
mvn clean install
```

## Running the Services

Each service can be run independently. Navigate to the service directory and use:

```
mvn spring-boot:run
```

For example, to run the student service:

```
cd student-service
mvn spring-boot:run
```

To run all services, you'll need to open separate terminal windows for each service.

## API Documentation

API endpoints for each service:

1. Student Service (Port 8081):

   - POST /api/students: Create a new student
   - GET /api/students/{id}: Get a student by ID
   - GET /api/students: Get all students
   - PUT /api/students/{id}: Update a student
   - DELETE /api/students/{id}: Delete a student
   - GET /api/students/{id}/academic-record: Get a student's academic record

2. Course Service (Port 8083):

   - POST /api/courses: Create a new course
   - GET /api/courses/{id}: Get a course by ID
   - GET /api/courses: Get all courses
   - PUT /api/courses/{id}: Update a course
   - DELETE /api/courses/{id}: Delete a course
   - POST /api/professors: Create a new professor
   - GET /api/professors/{id}: Get a professor by ID
   - GET /api/professors: Get all professors
   - PUT /api/professors/{id}: Update a professor
   - DELETE /api/professors/{id}: Delete a professor
   - POST /api/tutors: Create a new tutor
   - GET /api/tutors/{id}: Get a tutor by ID
   - GET /api/tutors: Get all tutors
   - PUT /api/tutors/{id}: Update a tutor
   - DELETE /api/tutors/{id}: Delete a tutor
   - POST /api/programs: Create a new program
   - GET /api/programs/{id}: Get a program by ID
   - GET /api/programs: Get all programs
   - PUT /api/programs/{id}: Update a program
   - DELETE /api/programs/{id}: Delete a program

3. Enrollment Service (Port 8082):
   - POST /api/enrollments: Create a new enrollment
   - GET /api/enrollments/{id}: Get an enrollment by ID
   - GET /api/enrollments: Get all enrollments
   - PUT /api/enrollments/{id}/status: Update enrollment status
   - DELETE /api/enrollments/{id}: Delete an enrollment
   - POST /api/enrollments/{enrollmentId}/grades: Add a grade to an enrollment
   - PUT /api/grades/{gradeId}: Update a grade
   - GET /api/grades/{gradeId}: Get a grade by ID
   - GET /api/enrollments/{enrollmentId}/grades: Get grades for an enrollment
   - GET /api/enrollments/student/{studentId}: Get enrollments for a student

## Complete API Documentation

Please read [Detail API Documentation](Detail_APIs_Documentation.md) for details API requests and responese.

## License

This project is licensed under the MIT License - see the LICENSE.md file for details.
