# University Course Management System

This project is a microservices-based implementation of a University Course Management System. It consists of three main services: Student Service, Course Service, and Enrollment Service.

## Project Structure

Each service follows a layered architecture:

- `api`: Controllers for handling HTTP requests
- `service`: Business logic implementation
- `domain`: Domain models and repository interfaces
- `infrastructure`: Data persistence and external service integrations


```
service-name/
├── api/
│   └── controllers/
├── service/
│   └── impl/
├── domain/
│   ├── model/
│   └── repository/
└── infrastructure/
    ├── persistence/
    └── external/
```

- `student-service`: Manages student information
- `course-service`: Handles course and professor data
- `enrollment-service`: Manages student enrollments and grades
- `common`: Shared utilities and models

## Prerequisites

- JDK 21
- Maven 3.8+

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

## API Documentation

API documentation for each service can be found in their respective directories.

## Contributing

Please read CONTRIBUTING.md for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the LICENSE.md file for details.
