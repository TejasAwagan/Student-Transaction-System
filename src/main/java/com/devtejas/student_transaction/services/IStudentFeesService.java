package com.devtejas.student_transaction.services;

import com.devtejas.student_transaction.entity.Student;
import com.devtejas.student_transaction.entity.StudentFees;
import com.devtejas.student_transaction.http.CommonHttpResponse;

import java.util.List;
import java.util.Optional;

public interface IStudentFeesService {
    CommonHttpResponse<List<StudentFees>> getAllStudentFees();


    CommonHttpResponse <StudentFees> findFeesRecordById(String id);

//    CommonHttpResponse<StudentFees> createStudentFees(StudentFees student);

//    CommonHttpResponse deleteStudentById(String id);
}
