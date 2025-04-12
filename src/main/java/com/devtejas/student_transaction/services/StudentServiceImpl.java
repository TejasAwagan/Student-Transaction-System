package com.devtejas.student_transaction.services;

import com.devtejas.student_transaction.entity.Student;
import com.devtejas.student_transaction.entity.StudentFees;
import com.devtejas.student_transaction.globalexceptions.BadRequestException;
import com.devtejas.student_transaction.globalexceptions.ConflictException;
import com.devtejas.student_transaction.globalexceptions.ResourceNotFoundException;
import com.devtejas.student_transaction.http.CommonHttpResponse;
import com.devtejas.student_transaction.repository.ICourseRepository;
import com.devtejas.student_transaction.repository.IStudentFeesRepository;
import com.devtejas.student_transaction.repository.IStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private IStudentRepository studentRepository;

    @Autowired
    private IStudentFeesRepository studentFeesRepository;

    @Autowired
    ICourseRepository courseRepository;

    @Autowired
    ICourseService courseService;

    @Override
    public CommonHttpResponse<List<Student>> getAllStudent() {

        var response = new CommonHttpResponse<List<Student>>();

        List<Student> students = studentRepository.findAll();

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
    public CommonHttpResponse<Student> createStudent(Student student) {
        var response = new CommonHttpResponse<Student>();

        // Check if student already exists based on registration number
        Optional<Student> existingStudent = studentRepository.findByRegistrationNumber(student.getRegistrationNumber());
        if (existingStudent.isPresent()) {
            throw new ConflictException("Student already exists with registration number: " + student.getRegistrationNumber());
        }

        // Check if the course exists based on courseId
        var courseOptional = courseRepository.findById(student.getCourseId());

        if (courseOptional.isEmpty()) {
            throw new ResourceNotFoundException("Course with ID " + student.getCourseId() + " does not exist");
        }

        student.setId(UUID.randomUUID().toString());
        student.setCourseId(student.getCourseId());
        student.setStudentName(student.getStudentName());

        Student savedStudent = studentRepository.save(student);

/////////////////////////////////////////////////////////

        StudentFees studentFees = new StudentFees();

        double totalFees = courseOptional.get().getFees();
        double paidFees = studentFees.getPaidFees() != null ? studentFees.getPaidFees() : 0.0; // Default to 0 if null
        double dueAmount = totalFees - paidFees;

        // Set payment status dynamically
        String paymentStatus;
        if (dueAmount == 0) {
            paymentStatus = "Paid";
        } else if (paidFees > 0) {
            paymentStatus = "Partial Paid";
        } else {
            paymentStatus = "Unpaid";
        }

        // Save StudentFees entity
        studentFees.setId(UUID.randomUUID().toString());
        studentFees.setStudentId(savedStudent.getId());
        studentFees.setTotalFees(totalFees);
        studentFees.setPaidFees(paidFees);
        studentFees.setDueAmount(dueAmount);
        studentFees.setPaymentStatus(paymentStatus);

        studentFeesRepository.save(studentFees);

        ////////////////////// Response Handling //////////////////////
        response.setSuccess(true);
        response.setMessage("Student created successfully");
        response.setResponseCode(201);
        response.setData(savedStudent);

        return response;

    }

    @Override
    public CommonHttpResponse<Student> updateStudent(Student student) {
        var response = new CommonHttpResponse<Student>();

        Optional<Student> studentExist = studentRepository.findByRegistrationNumber(student.getRegistrationNumber());

        if(!studentExist.isPresent()){
            response.setSuccess(false);
            response.setResponseCode(404);
            response.setMessage("Student with registration number - "+ student.getRegistrationNumber() + "  not Found");

            return  response;
        }

        Student newStudent = studentExist.get();

        if (student.getStudentName() != null && !student.getStudentName().isEmpty()) {
            newStudent.setStudentName(student.getStudentName());
        }
        if (student.getRegistrationNumber() != null && !student.getRegistrationNumber().isEmpty()) {
            newStudent.setRegistrationNumber(student.getRegistrationNumber());
        }
        if (student.getCourseId() != null && !student.getCourseId().isEmpty()) {
            newStudent.setCourseId(student.getCourseId());
        }

        Student s = studentRepository.save(newStudent);

        response.setSuccess(true);
        response.setMessage("Student with id - "+ student.getRegistrationNumber() + " Updated Successfully");
        response.setResponseCode(200);
        response.setData(s);

        return response;
    }


    @Override
    public CommonHttpResponse<Student> findStudentById(String id) {
        var response = new CommonHttpResponse<Student>();

        if(id == null || id.isEmpty()){
            throw new BadRequestException("Id cannot be null or empty");
        }

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with Id " + id + " not found!"));



        response.setSuccess(true);
        response.setResponseCode(200);
        response.setMessage("Student with Id - " + id + " found Successfully !");
        response.setData(student);
        return response;

    }

    @Override
    public CommonHttpResponse deleteStudentById(String id) {
        var response = new CommonHttpResponse<Student>();

        if (id == null || id.isEmpty()) {
            throw new BadRequestException("Id cannot be null or empty");
        }

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with Id " + id + "  not found!"));

        studentRepository.deleteById(id);

        response.setSuccess(true);
        response.setResponseCode(200);
        response.setMessage("Student with Id - " + id + " Deleted Successfully !");

        return response;

    }

}
