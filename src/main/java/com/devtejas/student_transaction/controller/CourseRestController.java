package com.devtejas.student_transaction.controller;

import com.devtejas.student_transaction.entity.Course;
import com.devtejas.student_transaction.http.CommonHttpResponse;
import com.devtejas.student_transaction.services.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseRestController {

    @Autowired
    public ICourseService courseImpl;


    @GetMapping("/getallcourses")
    public CommonHttpResponse<List<Course>> getAllCourses(){
        return courseImpl.getAllCourses();
    }

    @GetMapping("/getcourseById")
    public CommonHttpResponse<Course> getCourseById(@RequestParam String courseId){
       return courseImpl.findCourseById(courseId.toString());
    }

    @DeleteMapping("/deletecourse")
    public CommonHttpResponse deleteCourseById(@RequestParam String courseId){
        return courseImpl.deleteCourseById(courseId);
    }

    @PostMapping("/savecourse")
    public CommonHttpResponse addCourse(@RequestBody Course theCourse){
        return courseImpl.save(theCourse);
    }


}
