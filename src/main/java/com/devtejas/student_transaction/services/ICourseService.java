package com.devtejas.student_transaction.services;

import com.devtejas.student_transaction.entity.Course;
import com.devtejas.student_transaction.http.CommonHttpResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ICourseService {
    CommonHttpResponse <List<Course>> getAllCourses();

    CommonHttpResponse <Course> save(Course theCourse);

    CommonHttpResponse <Course> findCourseById(String id);

    CommonHttpResponse deleteCourseById(String id);

//    Double getFees(String id);
}
