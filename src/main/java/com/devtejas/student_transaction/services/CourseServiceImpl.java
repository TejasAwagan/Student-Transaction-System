package com.devtejas.student_transaction.services;

import com.devtejas.student_transaction.entity.Course;
import com.devtejas.student_transaction.globalexceptions.BadRequestException;
import com.devtejas.student_transaction.globalexceptions.ResourceNotFoundException;
import com.devtejas.student_transaction.http.CommonHttpResponse;
import com.devtejas.student_transaction.repository.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class CourseServiceImpl implements ICourseService {


    @Autowired
    private ICourseRepository courseRepository;

    @Override
    public CommonHttpResponse<List<Course>> getAllCourses() {
        var response = new CommonHttpResponse<List<Course>>();

        List<Course> courses = courseRepository.findAll();

        if (courses.isEmpty()) {
            response.setSuccess(false);
            response.setMessage("No courses found.");
            response.setResponseCode(404);
            response.setData(Collections.emptyList());
        } else {
            response.setSuccess(true);
            response.setMessage("Data Fetched Successfully!");
            response.setResponseCode(200);
            response.setData(courses);
        }

        return response;
    }

    @Override
    public CommonHttpResponse<Course> findCourseById(String id) {
        var response = new CommonHttpResponse<Course>();

        if(id == null || id.isEmpty()){
            throw new BadRequestException("Course ID cannot be null or empty");
        }

        Course course  = courseRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Course with ID " + id + " not found"));

        // If the course exists, get it and return the response
        response.setMessage("Course Found Successfully!");
        response.setResponseCode(200);
        response.setSuccess(true);
        response.setData(course);

        return response;
    }

    @Override
    public CommonHttpResponse<Course> save(Course theCourse) {
        var response = new CommonHttpResponse<Course>();
        // Check if a course with the same name already exists
        Optional<Course> existingCourse = courseRepository.findByName(theCourse.getName());

        if (existingCourse.isPresent()) {

            // If course exists, update its details
            Course courseToUpdate = existingCourse.get();
            courseToUpdate.setName(theCourse.getName());
            courseToUpdate.setFees(theCourse.getFees()); // Update fees
            courseToUpdate.setModificationDate(System.currentTimeMillis());

            // Save the updated course
            Course updatedCourse = courseRepository.save(courseToUpdate);

            response.setSuccess(true);
            response.setMessage("Course with name '" + theCourse.getName() + "' updated successfully.");
            response.setResponseCode(200);
            response.setData(updatedCourse);
        } else {
            // If course does not exist, create a new one
            Course newCourse = new Course();

            newCourse.setId(UUID.randomUUID().toString());
            newCourse.setName(theCourse.getName());
            newCourse.setFees(theCourse.getFees());
            newCourse.setCreationDate(System.currentTimeMillis());


            // Save the new course
            Course savedCourse = courseRepository.save(newCourse);

            response.setSuccess(true);
            response.setMessage("Course with name '" + theCourse.getName() + "' created successfully.");
            response.setResponseCode(201);
            response.setData(savedCourse);
        }

        return response;
    }

    @Override
    public CommonHttpResponse deleteCourseById(String id) {

        var response = new CommonHttpResponse<Course>();

        if (id == null || id.isEmpty()) {
            throw new BadRequestException("Course ID cannot be null or empty");
        }

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course with ID " + id + " not found"));

        courseRepository.deleteById(id);

        // If the course exists, get it and return the response
        response.setMessage("Course Deleted Successfully!");
        response.setResponseCode(200);
        response.setSuccess(true);
        response.setData(course);

        return response;

    }

//    @Override
//    public Double getFees(String id) {
//        var response = new CommonHttpResponse<Double>();
//
//        Optional<Course> courseExist = courseRepository.findById(id);
//
//        if(!courseExist.isPresent()){
//            System.out.println("Course with id not present");
//        }
//
//        return courseExist.get().getFees();
//    }
}
