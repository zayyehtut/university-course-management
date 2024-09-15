# University Course Management API Documentation

This documentation provides an overview of the University Course Management API, which is designed to manage students, courses, professors, programs, timetables, and enrollments within a university setting. The API is organized into several services, each handling specific entities and operations.

## Table of Contents

1. [Introduction](#introduction)
2. [API Endpoints](#api-endpoints)
   - [Student Service](#student-service)
   - [Course Service](#course-service)
   - [Enrollment Service](#enrollment-service)
3. [Sample Responses](#sample-responses)
4. [Running the Collection](#running-the-collection)
   - [Windows](#windows)
   - [macOS/Linux](#macoslinux)

## Introduction

The University Course Management API allows you to perform CRUD (Create, Read, Update, Delete) operations on various entities within a university system. The API is divided into three main services:

- **Student Service**: Manages student information, including academic records.
- **Course Service**: Manages courses, professors, programs, timetables, and tutors.
- **Enrollment Service**: Manages student enrollments and grades.

## API Endpoints

### Student Service

#### Create Student

- **Method**: `POST`
- **URL**: `http://localhost:8081/api/students`
- **Headers**:
  - `Content-Type: application/json`
- **Body**:
  ```json
  {
    "name": "John Doe",
    "email": "john.doe@example.com",
    "type": "UNDERGRADUATE",
    "address": "123 Main St",
    "phoneNumber": "1234567890",
    "dateOfBirth": "1990-01-01"
  }
  ```
- **Response**:

  - **Status Code**: `201 Created`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "name": "John Doe",
      "email": "john.doe@example.com",
      "type": "UNDERGRADUATE",
      "address": "123 Main St",
      "phoneNumber": "1234567890",
      "dateOfBirth": "1990-01-01"
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X POST -H "Content-Type:application/json" -d "{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"type\":\"UNDERGRADUATE\",\"address\":\"123 Main St\",\"phoneNumber\":\"1234567890\",\"dateOfBirth\":\"1990-01-01\"}" http://localhost:8081/api/students
    ```
  - **macOS/Linux**:
    ```bash
    curl -X POST -H "Content-Type:application/json" -d '{"name":"John Doe","email":"john.doe@example.com","type":"UNDERGRADUATE","address":"123 Main St","phoneNumber":"1234567890","dateOfBirth":"1990-01-01"}' http://localhost:8081/api/students
    ```

#### Get Student by ID

- **Method**: `GET`
- **URL**: `http://localhost:8081/api/students/{{studentId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "name": "John Doe",
      "email": "john.doe@example.com",
      "type": "UNDERGRADUATE",
      "address": "123 Main St",
      "phoneNumber": "1234567890",
      "dateOfBirth": "1990-01-01"
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X GET -H "Content-Type:application/json" http://localhost:8081/api/students/{{studentId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X GET -H "Content-Type:application/json" http://localhost:8081/api/students/{{studentId}}
    ```

### Get All Students

- **Method**: `GET`
- **URL**: `http://localhost:8081/api/students`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    [
      {
        "id": "12345678-1234-1234-1234-123456789012",
        "name": "John Doe",
        "email": "john.doe@example.com",
        "type": "UNDERGRADUATE",
        "address": "123 Main St",
        "phoneNumber": "1234567890",
        "dateOfBirth": "1990-01-01"
      },
      {
        "id": "87654321-4321-4321-4321-876543210987",
        "name": "Jane Smith",
        "email": "jane.smith@example.com",
        "type": "POSTGRADUATE",
        "address": "456 Elm St",
        "phoneNumber": "0987654321",
        "dateOfBirth": "1992-05-15"
      }
    ]
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X GET -H "Content-Type:application/json" http://localhost:8081/api/students
    ```
  - **macOS/Linux**:
    ```bash
    curl -X GET -H "Content-Type:application/json" http://localhost:8081/api/students
    ```

### Get Academic Record

- **Method**: `GET`
- **URL**: `http://localhost:8081/api/students/{{studentId}}/academic-record`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    {
      "studentId": "12345678-1234-1234-1234-123456789012",
      "courses": [
        {
          "courseId": "12345678-1234-1234-1234-123456789012",
          "courseName": "Introduction to Computer Science",
          "grade": "A",
          "credits": 3
        },
        {
          "courseId": "87654321-4321-4321-4321-876543210987",
          "courseName": "Data Structures",
          "grade": "B",
          "credits": 4
        }
      ],
      "totalCredits": 7,
      "gpa": 3.5
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X GET -H "Content-Type:application/json" http://localhost:8081/api/students/{{studentId}}/academic-record
    ```
  - **macOS/Linux**:
    ```bash
    curl -X GET -H "Content-Type:application/json" http://localhost:8081/api/students/{{studentId}}/academic-record
    ```

### Update Student

- **Method**: `PUT`
- **URL**: `http://localhost:8081/api/students/{{studentId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Body**:
  ```json
  {
    "name": "John Doe Updated",
    "email": "john.doe.updated@example.com",
    "type": "POSTGRADUATE",
    "address": "456 Main St",
    "phoneNumber": "0987654321",
    "dateOfBirth": "1990-01-01"
  }
  ```
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "name": "John Doe Updated",
      "email": "john.doe.updated@example.com",
      "type": "POSTGRADUATE",
      "address": "456 Main St",
      "phoneNumber": "0987654321",
      "dateOfBirth": "1990-01-01"
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X PUT -H "Content-Type:application/json" -d "{\"name\":\"John Doe Updated\",\"email\":\"john.doe.updated@example.com\",\"type\":\"POSTGRADUATE\",\"address\":\"456 Main St\",\"phoneNumber\":\"0987654321\",\"dateOfBirth\":\"1990-01-01\"}" http://localhost:8081/api/students/{{studentId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X PUT -H "Content-Type:application/json" -d '{"name":"John Doe Updated","email":"john.doe.updated@example.com","type":"POSTGRADUATE","address":"456 Main St","phoneNumber":"0987654321","dateOfBirth":"1990-01-01"}' http://localhost:8081/api/students/{{studentId}}
    ```

### Delete Student

- **Method**: `DELETE`
- **URL**: `http://localhost:8081/api/students/{{studentId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `204 No Content`

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8081/api/students/{{studentId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8081/api/students/{{studentId}}
    ```

### Course Service

#### Course

#### Create Course

- **Method**: `POST`
- **URL**: `http://localhost:8083/api/courses`
- **Headers**:
  - `Content-Type: application/json`
- **Body**:
  ```json
  {
    "code": "CS101",
    "name": "Introduction to Computer Science",
    "credits": 3,
    "type": "MANDATORY",
    "professorId": "{{professorId}}",
    "tutorIds": [],
    "programIds": []
  }
  ```
- **Response**:

  - **Status Code**: `201 Created`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "code": "CS101",
      "name": "Introduction to Computer Science",
      "credits": 3,
      "type": "MANDATORY",
      "professorId": "{{professorId}}",
      "tutorIds": [],
      "programIds": []
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X POST -H "Content-Type:application/json" -d "{\"code\":\"CS101\",\"name\":\"Introduction to Computer Science\",\"credits\":3,\"type\":\"MANDATORY\",\"professorId\":\"{{professorId}}\",\"tutorIds\":[],\"programIds\":[]}" http://localhost:8083/api/courses
    ```
  - **macOS/Linux**:
    ```bash
    curl -X POST -H "Content-Type:application/json" -d '{"code":"CS101","name":"Introduction to Computer Science","credits":3,"type":"MANDATORY","professorId":"{{professorId}}","tutorIds":[],"programIds":[]}' http://localhost:8083/api/courses
    ```

#### Get Course by ID

- **Method**: `GET`
- **URL**: `http://localhost:8083/api/courses/{{courseId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "code": "CS101",
      "name": "Introduction to Computer Science",
      "credits": 3,
      "type": "MANDATORY",
      "professorId": "{{professorId}}",
      "tutorIds": [],
      "programIds": []
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/courses/{{courseId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/courses/{{courseId}}
    ```

#### Get All Courses

- **Method**: `GET`
- **URL**: `http://localhost:8083/api/courses`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    [
      {
        "id": "12345678-1234-1234-1234-123456789012",
        "code": "CS101",
        "name": "Introduction to Computer Science",
        "credits": 3,
        "type": "MANDATORY",
        "professorId": "{{professorId}}",
        "tutorIds": [],
        "programIds": []
      },
      {
        "id": "87654321-4321-4321-4321-876543210987",
        "code": "CS102",
        "name": "Data Structures",
        "credits": 4,
        "type": "ELECTIVE",
        "professorId": "{{professorId}}",
        "tutorIds": [],
        "programIds": []
      }
    ]
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/courses
    ```
  - **macOS/Linux**:
    ```bash
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/courses
    ```

#### Update Course

- **Method**: `PUT`
- **URL**: `http://localhost:8083/api/courses/{{courseId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Body**:
  ```json
  {
    "name": "Introduction to Computer Science Updated",
    "credits": 4,
    "type": "ELECTIVE",
    "professorId": "{{professorId}}",
    "tutorIds": [],
    "programIds": []
  }
  ```
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "code": "CS101",
      "name": "Introduction to Computer Science Updated",
      "credits": 4,
      "type": "ELECTIVE",
      "professorId": "{{professorId}}",
      "tutorIds": [],
      "programIds": []
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X PUT -H "Content-Type:application/json" -d "{\"name\":\"Introduction to Computer Science Updated\",\"credits\":4,\"type\":\"ELECTIVE\",\"professorId\":\"{{professorId}}\",\"tutorIds\":[],\"programIds\":[]}" http://localhost:8083/api/courses/{{courseId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X PUT -H "Content-Type:application/json" -d '{"name":"Introduction to Computer Science Updated","credits":4,"type":"ELECTIVE","professorId":"{{professorId}}","tutorIds":[],"programIds":[]}' http://localhost:8083/api/courses/{{courseId}}
    ```

#### Delete Course

- **Method**: `DELETE`
- **URL**: `http://localhost:8083/api/courses/{{courseId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `204 No Content`

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8083/api/courses/{{courseId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8083/api/courses/{{courseId}}
    ```

#### Get Tutors for Course

- **Method**: `GET`
- **URL**: `http://localhost:8083/api/courses/{{courseId}}/tutors`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    [
      {
        "id": "12345678-1234-1234-1234-123456789012",
        "name": "Alice Johnson",
        "email": "alice.johnson@example.com",
        "specialization": "Computer Science"
      },
      {
        "id": "87654321-4321-4321-4321-876543210987",
        "name": "Bob Smith",
        "email": "bob.smith@example.com",
        "specialization": "Computer Science"
      }
    ]
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/courses/{{courseId}}/tutors
    ```
  - **macOS/Linux**:
    ```bash
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/courses/{{courseId}}/tutors
    ```

#### Assign Tutor to Course

- **Method**: `POST`
- **URL**: `http://localhost:8083/api/courses/{{courseId}}/tutors/{{tutorId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `201 Created`

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X POST -H "Content-Type:application/json" http://localhost:8083/api/courses/{{courseId}}/tutors/{{tutorId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X POST -H "Content-Type:application/json" http://localhost:8083/api/courses/{{courseId}}/tutors/{{tutorId}}
    ```

#### Remove Tutor from Course

- **Method**: `DELETE`
- **URL**: `http://localhost:8083/api/courses/{{courseId}}/tutors/{{tutorId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `204 No Content`

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8083/api/courses/{{courseId}}/tutors/{{tutorId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8083/api/courses/{{courseId}}/tutors/{{tutorId}}
    ```

#### Add Timetable to Course

- **Method**: `POST`
- **URL**: `http://localhost:8083/api/courses/{{courseId}}/timetables`
- **Headers**:
  - `Content-Type: application/json`
- **Body**:
  ```json
  {
    "dayOfWeek": "MONDAY",
    "startTime": "09:00",
    "endTime": "10:30",
    "location": "Room 101"
  }
  ```
- **Response**:

  - **Status Code**: `201 Created`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "courseId": "{{courseId}}",
      "dayOfWeek": "MONDAY",
      "startTime": "09:00",
      "endTime": "10:30",
      "location": "Room 101"
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X POST -H "Content-Type:application/json" -d "{\"dayOfWeek\":\"MONDAY\",\"startTime\":\"09:00\",\"endTime\":\"10:30\",\"location\":\"Room 101\"}" http://localhost:8083/api/courses/{{courseId}}/timetables
    ```
  - **macOS/Linux**:
    ```bash
    curl -X POST -H "Content-Type:application/json" -d '{"dayOfWeek":"MONDAY","startTime":"09:00","endTime":"10:30","location":"Room 101"}' http://localhost:8083/api/courses/{{courseId}}/timetables
    ```

#### Remove Timetable from Course

- **Method**: `DELETE`
- **URL**: `http://localhost:8083/api/courses/{{courseId}}/timetables/{{timetableId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `204 No Content`

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8083/api/courses/{{courseId}}/timetables/{{timetableId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8083/api/courses/{{courseId}}/timetables/{{timetableId}}
    ```

#### Progfessor

##### Create Professor

- **Method**: `POST`
- **URL**: `http://localhost:8083/api/professors`
- **Headers**:
  - `Content-Type: application/json`
- **Body**:
  ```json
  {
    "name": "Dr. Jane Smith",
    "department": "Computer Science"
  }
  ```
- **Response**:

  - **Status Code**: `201 Created`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "name": "Dr. Jane Smith",
      "department": "Computer Science"
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X POST -H "Content-Type:application/json" -d "{\"name\":\"Dr. Jane Smith\",\"department\":\"Computer Science\"}" http://localhost:8083/api/professors
    ```
  - **macOS/Linux**:
    ```bash
    curl -X POST -H "Content-Type:application/json" -d '{"name":"Dr. Jane Smith","department":"Computer Science"}' http://localhost:8083/api/professors
    ```

##### Get Professor by ID

- **Method**: `GET`
- **URL**: `http://localhost:8083/api/professors/{{professorId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "name": "Dr. Jane Smith",
      "department": "Computer Science"
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/professors/{{professorId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/professors/{{professorId}}
    ```

##### Get All Professors

- **Method**: `GET`
- **URL**: `http://localhost:8083/api/professors`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    [
      {
        "id": "12345678-1234-1234-1234-123456789012",
        "name": "Dr. Jane Smith",
        "department": "Computer Science"
      },
      {
        "id": "87654321-4321-4321-4321-876543210987",
        "name": "Dr. John Doe",
        "department": "Mathematics"
      }
    ]
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/professors
    ```
  - **macOS/Linux**:
    ```bash
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/professors
    ```

##### Update Professor

- **Method**: `PUT`
- **URL**: `http://localhost:8083/api/professors/{{professorId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Body**:
  ```json
  {
    "name": "Dr. Jane Smith Updated",
    "department": "Electrical Engineering"
  }
  ```
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "name": "Dr. Jane Smith Updated",
      "department": "Electrical Engineering"
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X PUT -H "Content-Type:application/json" -d "{\"name\":\"Dr. Jane Smith Updated\",\"department\":\"Electrical Engineering\"}" http://localhost:8083/api/professors/{{professorId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X PUT -H "Content-Type:application/json" -d '{"name":"Dr. Jane Smith Updated","department":"Electrical Engineering"}' http://localhost:8083/api/professors/{{professorId}}
    ```

##### Delete Professor

- **Method**: `DELETE`
- **URL**: `http://localhost:8083/api/professors/{{professorId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `204 No Content`

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8083/api/professors/{{professorId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8083/api/professors/{{professorId}}
    ```

#### Programs

##### Create Program

- **Method**: `POST`
- **URL**: `http://localhost:8083/api/programs`
- **Headers**:
  - `Content-Type: application/json`
- **Body**:
  ```json
  {
    "name": "Computer Science",
    "description": "Bachelor of Science in Computer Science",
    "degreeType": "BACHELOR",
    "requiredCredits": 120
  }
  ```
- **Response**:

  - **Status Code**: `201 Created`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "name": "Computer Science",
      "description": "Bachelor of Science in Computer Science",
      "degreeType": "BACHELOR",
      "requiredCredits": 120
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X POST -H "Content-Type:application/json" -d "{\"name\":\"Computer Science\",\"description\":\"Bachelor of Science in Computer Science\",\"degreeType\":\"BACHELOR\",\"requiredCredits\":120}" http://localhost:8083/api/programs
    ```
  - **macOS/Linux**:
    ```bash
    curl -X POST -H "Content-Type:application/json" -d '{"name":"Computer Science","description":"Bachelor of Science in Computer Science","degreeType":"BACHELOR","requiredCredits":120}' http://localhost:8083/api/programs
    ```

##### Get Program by ID

- **Method**: `GET`
- **URL**: `http://localhost:8083/api/programs/{{programId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "name": "Computer Science",
      "description": "Bachelor of Science in Computer Science",
      "degreeType": "BACHELOR",
      "requiredCredits": 120
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/programs/{{programId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/programs/{{programId}}
    ```

##### Get All Programs

- **Method**: `GET`
- **URL**: `http://localhost:8083/api/programs`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    [
      {
        "id": "12345678-1234-1234-1234-123456789012",
        "name": "Computer Science",
        "description": "Bachelor of Science in Computer Science",
        "degreeType": "BACHELOR",
        "requiredCredits": 120
      },
      {
        "id": "87654321-4321-4321-4321-876543210987",
        "name": "Electrical Engineering",
        "description": "Bachelor of Science in Electrical Engineering",
        "degreeType": "BACHELOR",
        "requiredCredits": 130
      }
    ]
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/programs
    ```
  - **macOS/Linux**:
    ```bash
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/programs
    ```

##### Update Program

- **Method**: `PUT`
- **URL**: `http://localhost:8083/api/programs/{{programId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Body**:
  ```json
  {
    "name": "Computer Science Updated",
    "description": "Bachelor of Science in Computer Science Updated",
    "degreeType": "MASTER",
    "requiredCredits": 150
  }
  ```
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "name": "Computer Science Updated",
      "description": "Bachelor of Science in Computer Science Updated",
      "degreeType": "MASTER",
      "requiredCredits": 150
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X PUT -H "Content-Type:application/json" -d "{\"name\":\"Computer Science Updated\",\"description\":\"Bachelor of Science in Computer Science Updated\",\"degreeType\":\"MASTER\",\"requiredCredits\":150}" http://localhost:8083/api/programs/{{programId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X PUT -H "Content-Type:application/json" -d '{"name":"Computer Science Updated","description":"Bachelor of Science in Computer Science Updated","degreeType":"MASTER","requiredCredits":150}' http://localhost:8083/api/programs/{{programId}}
    ```

##### Delete Program

- **Method**: `DELETE`
- **URL**: `http://localhost:8083/api/programs/{{programId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `204 No Content`

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8083/api/programs/{{programId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8083/api/programs/{{programId}}
    ```

##### Assign Course to Program

- **Method**: `POST`
- **URL**: `http://localhost:8083/api/programs/{{programId}}/courses/{{courseId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `201 Created`

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X POST -H "Content-Type:application/json" http://localhost:8083/api/programs/{{programId}}/courses/{{courseId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X POST -H "Content-Type:application/json" http://localhost:8083/api/programs/{{programId}}/courses/{{courseId}}
    ```

##### Remove Course from Program

- **Method**: `DELETE`
- **URL**: `http://localhost:8083/api/programs/{{programId}}/courses/{{courseId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `204 No Content`

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8083/api/programs/{{programId}}/courses/{{courseId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8083/api/programs/{{programId}}/courses/{{courseId}}
    ```

#### Timetables

##### Create Timetable

- **Method**: `POST`
- **URL**: `http://localhost:8083/api/timetables`
- **Headers**:
  - `Content-Type: application/json`
- **Body**:
  ```json
  {
    "courseId": "{{courseId}}",
    "dayOfWeek": "MONDAY",
    "startTime": "09:00",
    "endTime": "10:30",
    "location": "Room 101"
  }
  ```
- **Response**:

  - **Status Code**: `201 Created`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "courseId": "{{courseId}}",
      "dayOfWeek": "MONDAY",
      "startTime": "09:00",
      "endTime": "10:30",
      "location": "Room 101"
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X POST -H "Content-Type:application/json" -d "{\"courseId\":\"{{courseId}}\",\"dayOfWeek\":\"MONDAY\",\"startTime\":\"09:00\",\"endTime\":\"10:30\",\"location\":\"Room 101\"}" http://localhost:8083/api/timetables
    ```
  - **macOS/Linux**:
    ```bash
    curl -X POST -H "Content-Type:application/json" -d '{"courseId":"{{courseId}}","dayOfWeek":"MONDAY","startTime":"09:00","endTime":"10:30","location":"Room 101"}' http://localhost:8083/api/timetables
    ```

##### Get Timetable by ID

- **Method**: `GET`
- **URL**: `http://localhost:8083/api/timetables/{{timetableId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "courseId": "{{courseId}}",
      "dayOfWeek": "MONDAY",
      "startTime": "09:00",
      "endTime": "10:30",
      "location": "Room 101"
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/timetables/{{timetableId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/timetables/{{timetableId}}
    ```

##### Get Timetables by Course ID

- **Method**: `GET`
- **URL**: `http://localhost:8083/api/timetables/course/{{courseId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    [
      {
        "id": "12345678-1234-1234-1234-123456789012",
        "courseId": "{{courseId}}",
        "dayOfWeek": "MONDAY",
        "startTime": "09:00",
        "endTime": "10:30",
        "location": "Room 101"
      },
      {
        "id": "87654321-4321-4321-4321-876543210987",
        "courseId": "{{courseId}}",
        "dayOfWeek": "WEDNESDAY",
        "startTime": "10:00",
        "endTime": "11:30",
        "location": "Room 102"
      }
    ]
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/timetables/course/{{courseId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/timetables/course/{{courseId}}
    ```

##### Update Timetable

- **Method**: `PUT`
- **URL**: `http://localhost:8083/api/timetables/{{timetableId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Body**:
  ```json
  {
    "courseId": "{{courseId}}",
    "dayOfWeek": "TUESDAY",
    "startTime": "10:00",
    "endTime": "11:30",
    "location": "Room 102"
  }
  ```
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "courseId": "{{courseId}}",
      "dayOfWeek": "TUESDAY",
      "startTime": "10:00",
      "endTime": "11:30",
      "location": "Room 102"
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X PUT -H "Content-Type:application/json" -d "{\"courseId\":\"{{courseId}}\",\"dayOfWeek\":\"TUESDAY\",\"startTime\":\"10:00\",\"endTime\":\"11:30\",\"location\":\"Room 102\"}" http://localhost:8083/api/timetables/{{timetableId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X PUT -H "Content-Type:application/json" -d '{"courseId":"{{courseId}}","dayOfWeek":"TUESDAY","startTime":"10:00","endTime":"11:30","location":"Room 102"}' http://localhost:8083/api/timetables/{{timetableId}}
    ```

##### Delete Timetable

- **Method**: `DELETE`
- **URL**: `http://localhost:8083/api/timetables/{{timetableId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `204 No Content`

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8083/api/timetables/{{timetableId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8083/api/timetables/{{timetableId}}
    ```

#### Tutors

##### Create Tutor

- **Method**: `POST`
- **URL**: `http://localhost:8083/api/tutors`
- **Headers**:
  - `Content-Type: application/json`
- **Body**:
  ```json
  {
    "name": "Alice Johnson",
    "email": "alice.johnson@example.com",
    "specialization": "Computer Science"
  }
  ```
- **Response**:

  - **Status Code**: `201 Created`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "name": "Alice Johnson",
      "email": "alice.johnson@example.com",
      "specialization": "Computer Science"
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X POST -H "Content-Type:application/json" -d "{\"name\":\"Alice Johnson\",\"email\":\"alice.johnson@example.com\",\"specialization\":\"Computer Science\"}" http://localhost:8083/api/tutors
    ```
  - **macOS/Linux**:
    ```bash
    curl -X POST -H "Content-Type:application/json" -d '{"name":"Alice Johnson","email":"alice.johnson@example.com","specialization":"Computer Science"}' http://localhost:8083/api/tutors
    ```

##### Get Tutor by ID

- **Method**: `GET`
- **URL**: `http://localhost:8083/api/tutors/{{tutorId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "name": "Alice Johnson",
      "email": "alice.johnson@example.com",
      "specialization": "Computer Science"
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/tutors/{{tutorId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/tutors/{{tutorId}}
    ```

##### Get All Tutors

- **Method**: `GET`
- **URL**: `http://localhost:8083/api/tutors`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    [
      {
        "id": "12345678-1234-1234-1234-123456789012",
        "name": "Alice Johnson",
        "email": "alice.johnson@example.com",
        "specialization": "Computer Science"
      },
      {
        "id": "87654321-4321-4321-4321-876543210987",
        "name": "Bob Smith",
        "email": "bob.smith@example.com",
        "specialization": "Mathematics"
      }
    ]
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/tutors
    ```
  - **macOS/Linux**:
    ```bash
    curl -X GET -H "Content-Type:application/json" http://localhost:8083/api/tutors
    ```

##### Update Tutor

- **Method**: `PUT`
- **URL**: `http://localhost:8083/api/tutors/{{tutorId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Body**:
  ```json
  {
    "name": "Alice Johnson Updated",
    "email": "alice.johnson.updated@example.com",
    "specialization": "Electrical Engineering"
  }
  ```
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "name": "Alice Johnson Updated",
      "email": "alice.johnson.updated@example.com",
      "specialization": "Electrical Engineering"
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X PUT -H "Content-Type:application/json" -d "{\"name\":\"Alice Johnson Updated\",\"email\":\"alice.johnson.updated@example.com\",\"specialization\":\"Electrical Engineering\"}" http://localhost:8083/api/tutors/{{tutorId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X PUT -H "Content-Type:application/json" -d '{"name":"Alice Johnson Updated","email":"alice.johnson.updated@example.com","specialization":"Electrical Engineering"}' http://localhost:8083/api/tutors/{{tutorId}}
    ```

##### Delete Tutor

- **Method**: `DELETE`
- **URL**: `http://localhost:8083/api/tutors/{{tutorId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `204 No Content`

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8083/api/tutors/{{tutorId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8083/api/tutors/{{tutorId}}
    ```

##### Assign Tutor to Course

- **Method**: `POST`
- **URL**: `http://localhost:8083/api/tutors/{{tutorId}}/courses/{{courseId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `201 Created`

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X POST -H "Content-Type:application/json" http://localhost:8083/api/tutors/{{tutorId}}/courses/{{courseId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X POST -H "Content-Type:application/json" http://localhost:8083/api/tutors/{{tutorId}}/courses/{{courseId}}
    ```

##### Remove Tutor from Course

- **Method**: `DELETE`
- **URL**: `http://localhost:8083/api/tutors/{{tutorId}}/courses/{{courseId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `204 No Content`

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8083/api/tutors/{{tutorId}}/courses/{{courseId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8083/api/tutors/{{tutorId}}/courses/{{courseId}}
    ```

### Running the Collection

To run the Postman collection, you can use the following commands:

#### Windows

```shell
newman run University_Course_Management.postman_collection.json --env-var "studentServiceBaseUrl=http://localhost:8081" --env-var "courseServiceBaseUrl=http://localhost:8083" --env-var "enrollmentServiceBaseUrl=http://localhost:8082"
```

#### macOS/Linux

```bash
newman run University_Course_Management.postman_collection.json --env-var "studentServiceBaseUrl=http://localhost:8081" --env-var "courseServiceBaseUrl=http://localhost:8083" --env-var "enrollmentServiceBaseUrl=http://localhost:8082"
```

### Enrollment Service

#### Create Enrollment

- **Method**: `POST`
- **URL**: `http://localhost:8082/api/enrollments`
- **Headers**:
  - `Content-Type: application/json`
- **Body**:
  ```json
  {
    "studentId": "{{studentId}}",
    "courseId": "{{courseId}}",
    "courseName": "Introduction to Computer Science",
    "semester": "Fall 2023"
  }
  ```
- **Response**:

  - **Status Code**: `201 Created`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "studentId": "{{studentId}}",
      "courseId": "{{courseId}}",
      "courseName": "Introduction to Computer Science",
      "semester": "Fall 2023"
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X POST -H "Content-Type:application/json" -d "{\"studentId\":\"{{studentId}}\",\"courseId\":\"{{courseId}}\",\"courseName\":\"Introduction to Computer Science\",\"semester\":\"Fall 2023\"}" http://localhost:8082/api/enrollments
    ```
  - **macOS/Linux**:
    ```bash
    curl -X POST -H "Content-Type:application/json" -d '{"studentId":"{{studentId}}","courseId":"{{courseId}}","courseName":"Introduction to Computer Science","semester":"Fall 2023"}' http://localhost:8082/api/enrollments
    ```

#### Get Enrollment by ID

- **Method**: `GET`
- **URL**: `http://localhost:8082/api/enrollments/{{enrollmentId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "studentId": "{{studentId}}",
      "courseId": "{{courseId}}",
      "courseName": "Introduction to Computer Science",
      "semester": "Fall 2023"
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X GET -H "Content-Type:application/json" http://localhost:8082/api/enrollments/{{enrollmentId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X GET -H "Content-Type:application/json" http://localhost:8082/api/enrollments/{{enrollmentId}}
    ```

#### Get All Enrollments

- **Method**: `GET`
- **URL**: `http://localhost:8082/api/enrollments`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    [
      {
        "id": "12345678-1234-1234-1234-123456789012",
        "studentId": "{{studentId}}",
        "courseId": "{{courseId}}",
        "courseName": "Introduction to Computer Science",
        "semester": "Fall 2023"
      },
      {
        "id": "87654321-4321-4321-4321-876543210987",
        "studentId": "{{studentId}}",
        "courseId": "{{courseId}}",
        "courseName": "Data Structures",
        "semester": "Spring 2023"
      }
    ]
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X GET -H "Content-Type:application/json" http://localhost:8082/api/enrollments
    ```
  - **macOS/Linux**:
    ```bash
    curl -X GET -H "Content-Type:application/json" http://localhost:8082/api/enrollments
    ```

#### Update Enrollment Status

- **Method**: `PUT`
- **URL**: `http://localhost:8082/api/enrollments/{{enrollmentId}}/status?status=COMPLETED`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "studentId": "{{studentId}}",
      "courseId": "{{courseId}}",
      "courseName": "Introduction to Computer Science",
      "semester": "Fall 2023",
      "status": "COMPLETED"
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X PUT -H "Content-Type:application/json" http://localhost:8082/api/enrollments/{{enrollmentId}}/status?status=COMPLETED
    ```
  - **macOS/Linux**:
    ```bash
    curl -X PUT -H "Content-Type:application/json" http://localhost:8082/api/enrollments/{{enrollmentId}}/status?status=COMPLETED
    ```

#### Delete Enrollment

- **Method**: `DELETE`
- **URL**: `http://localhost:8082/api/enrollments/{{enrollmentId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `204 No Content`

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8082/api/enrollments/{{enrollmentId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X DELETE -H "Content-Type:application/json" http://localhost:8082/api/enrollments/{{enrollmentId}}
    ```

#### Add Grade

- **Method**: `POST`
- **URL**: `http://localhost:8082/api/enrollments/{{enrollmentId}}/grades`
- **Headers**:
  - `Content-Type: application/json`
- **Body**:
  ```json
  {
    "letterGrade": "A",
    "numericGrade": 4.0
  }
  ```
- **Response**:

  - **Status Code**: `201 Created`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "enrollmentId": "{{enrollmentId}}",
      "letterGrade": "A",
      "numericGrade": 4.0
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X POST -H "Content-Type:application/json" -d "{\"letterGrade\":\"A\",\"numericGrade\":4.0}" http://localhost:8082/api/enrollments/{{enrollmentId}}/grades
    ```
  - **macOS/Linux**:
    ```bash
    curl -X POST -H "Content-Type:application/json" -d '{"letterGrade":"A","numericGrade":4.0}' http://localhost:8082/api/enrollments/{{enrollmentId}}/grades
    ```

#### Update Grade

- **Method**: `PUT`
- **URL**: `http://localhost:8082/api/enrollments/grades/{{gradeId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Body**:
  ```json
  {
    "letterGrade": "B",
    "numericGrade": 3.0
  }
  ```
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "enrollmentId": "{{enrollmentId}}",
      "letterGrade": "B",
      "numericGrade": 3.0
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X PUT -H "Content-Type:application/json" -d "{\"letterGrade\":\"B\",\"numericGrade\":3.0}" http://localhost:8082/api/enrollments/grades/{{gradeId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X PUT -H "Content-Type:application/json" -d '{"letterGrade":"B","numericGrade":3.0}' http://localhost:8082/api/enrollments/grades/{{gradeId}}
    ```

#### Get Grade by ID

- **Method**: `GET`
- **URL**: `http://localhost:8082/api/enrollments/grades/{{gradeId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    {
      "id": "12345678-1234-1234-1234-123456789012",
      "enrollmentId": "{{enrollmentId}}",
      "letterGrade": "B",
      "numericGrade": 3.0
    }
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X GET -H "Content-Type:application/json" http://localhost:8082/api/enrollments/grades/{{gradeId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X GET -H "Content-Type:application/json" http://localhost:8082/api/enrollments/grades/{{gradeId}}
    ```

#### Get Grades by Enrollment ID

- **Method**: `GET`
- **URL**: `http://localhost:8082/api/enrollments/{{enrollmentId}}/grades`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    [
      {
        "id": "12345678-1234-1234-1234-123456789012",
        "enrollmentId": "{{enrollmentId}}",
        "letterGrade": "A",
        "numericGrade": 4.0
      },
      {
        "id": "87654321-4321-4321-4321-876543210987",
        "enrollmentId": "{{enrollmentId}}",
        "letterGrade": "B",
        "numericGrade": 3.0
      }
    ]
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X GET -H "Content-Type:application/json" http://localhost:8082/api/enrollments/{{enrollmentId}}/grades
    ```
  - **macOS/Linux**:
    ```bash
    curl -X GET -H "Content-Type:application/json" http://localhost:8082/api/enrollments/{{enrollmentId}}/grades
    ```

#### Get Enrollments by Student ID

- **Method**: `GET`
- **URL**: `http://localhost:8082/api/enrollments/student/{{studentId}}`
- **Headers**:
  - `Content-Type: application/json`
- **Response**:

  - **Status Code**: `200 OK`
  - **Body**:
    ```json
    [
      {
        "id": "12345678-1234-1234-1234-123456789012",
        "studentId": "{{studentId}}",
        "courseId": "{{courseId}}",
        "courseName": "Introduction to Computer Science",
        "semester": "Fall 2023"
      },
      {
        "id": "87654321-4321-4321-4321-876543210987",
        "studentId": "{{studentId}}",
        "courseId": "{{courseId}}",
        "courseName": "Data Structures",
        "semester": "Spring 2023"
      }
    ]
    ```

- **Command**:
  - **Windows CMD**:
    ```shell
    curl -X GET -H "Content-Type:application/json" http://localhost:8082/api/enrollments/student/{{studentId}}
    ```
  - **macOS/Linux**:
    ```bash
    curl -X GET -H "Content-Type:application/json" http://localhost:8082/api/enrollments/student/{{studentId}}
    ```
