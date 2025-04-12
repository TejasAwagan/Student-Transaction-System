package com.devtejas.student_transaction.controller;

import com.devtejas.student_transaction.entity.Course;
import com.devtejas.student_transaction.entity.Student;
import com.devtejas.student_transaction.http.CommonHttpResponse;
import com.devtejas.student_transaction.services.IStudentService;
import com.devtejas.student_transaction.services.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentRestController {

    private IStudentService studentService;

    public StudentRestController(IStudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/getallstudent")
    public CommonHttpResponse<List<Student>> getAllStudent(){
        return studentService.getAllStudent();
    }

    @PostMapping("/createstudent")
    public CommonHttpResponse<Student> addStudent(@RequestBody Student theStudent){
        return studentService.createStudent(theStudent);
    }

    @DeleteMapping("/deleteStudentById")
    public CommonHttpResponse<Student> deleteStudent(@RequestParam String id){
        return studentService.deleteStudentById(id);
    }

    @GetMapping("/findstudentbyId")
    public CommonHttpResponse<Student> findById(@RequestParam String id){
        return studentService.findStudentById(id);
    }

    @PostMapping("/updateStudent")
    public CommonHttpResponse<Student> updateStudent(@RequestBody Student student){
        return studentService.updateStudent(student);
    }
}
