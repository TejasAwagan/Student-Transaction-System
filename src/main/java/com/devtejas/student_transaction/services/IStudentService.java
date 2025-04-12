package com.devtejas.student_transaction.services;

import com.devtejas.student_transaction.entity.Student;
import com.devtejas.student_transaction.http.CommonHttpResponse;

import java.util.List;

public interface   IStudentService {

    CommonHttpResponse<List<Student>> getAllStudent();

    CommonHttpResponse <Student> createStudent(Student student);

    CommonHttpResponse <Student> updateStudent(Student student);

    CommonHttpResponse <Student> findStudentById(String id);

    CommonHttpResponse deleteStudentById(String id);
}
