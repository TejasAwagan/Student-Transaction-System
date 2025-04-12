package com.devtejas.student_transaction.controller;

import com.devtejas.student_transaction.entity.StudentFees;
import com.devtejas.student_transaction.http.CommonHttpResponse;
import com.devtejas.student_transaction.services.IStudentFeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studentfees")
public class StudentFeesRestController {

    @Autowired
    private IStudentFeesService studentFeesService;

    @GetMapping("/getallstudentfees")
    public CommonHttpResponse<List<StudentFees>> getAllStudentFees(){
        return studentFeesService.getAllStudentFees();
    }

//    @PostMapping("/createFeesRecord")
//    public CommonHttpResponse<StudentFees> createStudentFeesRecord(@RequestBody StudentFees studentFees){
//        return studentFeesService.createStudentFees(studentFees);
//    }

    @GetMapping("/findFeesRecordById")
    public CommonHttpResponse<StudentFees> findRecord(@RequestParam String id){
        return studentFeesService.findFeesRecordById(id);
    }
}
