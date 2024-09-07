package com.university.student.service;

import com.university.student.api.dto.StudentDTO;
import com.university.student.api.dto.AcademicRecordDTO;
import com.university.student.api.dto.CreateStudentRequest;
import com.university.student.api.dto.UpdateStudentRequest;

import java.util.List;

public interface StudentService {
    StudentDTO createStudent(CreateStudentRequest request);
    StudentDTO getStudentById(String id);
    List<StudentDTO> getAllStudents();
    StudentDTO updateStudent(String id, UpdateStudentRequest request);
    void deleteStudent(String id);
    AcademicRecordDTO getAcademicRecord(String studentId);

}