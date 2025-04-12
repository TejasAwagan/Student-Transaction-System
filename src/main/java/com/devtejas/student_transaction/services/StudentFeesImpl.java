package com.devtejas.student_transaction.services;

import com.devtejas.student_transaction.entity.Student;
import com.devtejas.student_transaction.entity.StudentFees;
import com.devtejas.student_transaction.http.CommonHttpResponse;
import com.devtejas.student_transaction.repository.IStudentFeesRepository;
import com.devtejas.student_transaction.repository.IStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentFeesImpl implements IStudentFeesService{

    @Autowired
    private IStudentFeesRepository studentFeesRepository;

    @Autowired
    private IStudentRepository studentService;

    @Override
    public CommonHttpResponse<List<StudentFees>> getAllStudentFees() {
        var response = new CommonHttpResponse<List<StudentFees>>();

        List<StudentFees> students = studentFeesRepository.findAll();

        if (students.isEmpty()) {
            response.setSuccess(false);
            response.setMessage("No Student found.");
            response.setResponseCode(404);
            response.setData(Collections.emptyList());
        } else {
            response.setSuccess(true);
            response.setMessage("Data Fetched Successfully!");
            response.setResponseCode(200);
            response.setData(students);
        }

        return response;

    }

    @Override
    public CommonHttpResponse<StudentFees> findFeesRecordById(String id) {
        var response = new CommonHttpResponse<StudentFees>();

        if(id==null || id.isEmpty()){
            response.setSuccess(false);
            response.setMessage("Id Could Not Be Zero or Empty");

            return response;
        }

        Optional<StudentFees> existRecord = studentFeesRepository.findById(id);

        if(!existRecord.isPresent()){
            response.setSuccess(false);
            response.setMessage("Fees Record not Found with id - "+ id);
            response.setResponseCode(404);

            return response;
        }

        StudentFees record = existRecord.get();

        response.setResponseCode(200);
        response.setSuccess(true);
        response.setMessage("Fees record Found Successfuly !");
        response.setData(record);

        return response;
    }

//    @Override
//    public CommonHttpResponse<StudentFees> createStudentFees(StudentFees student) {
//        var response = new CommonHttpResponse<StudentFees>();
//        Student tempStudent = new Student();
//
//        Optional<Student> existStudent = studentService.findById(tempStudent.getId());
//
//        if(!existStudent.isPresent()){
//            //create new student fees record
//            StudentFees newStudfees = new StudentFees();
//
//            newStudfees.setFees_id(UUID.randomUUID().toString());
//            newStudfees.setTotalFees(student.getTotalFees());
//            newStudfees.setPaymentStatus(student.getPaymentStatus());
//
//            response.setResponseCode(201);
//            response.setSuccess(true);
//            response.setMessage("New Student Fees Created !");
//            response.setData(newStudfees);
//
//            return response;
//        }

        //update the student fees record
//        StudentFees updateFees = studentFeesRepository.findById(studentService.findById(student.getStudent().getId()));

//        updateFees.setTotalFees(student.getTotalFees());
//        updateFees.setPaymentStatus(student.getPaymentStatus());
//
//        response.setSuccess(true);
//        response.setResponseCode(200);
//        response.setMessage("Fees record of Student with id - " + student.getStudent() + " Updated Successfully !");
//        response.setData(updateFees);

//        return response;
//    }

}
