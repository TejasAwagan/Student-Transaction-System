package com.devtejas.student_transaction.repository;

import com.devtejas.student_transaction.entity.Course;
import com.devtejas.student_transaction.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface IStudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByStudentName(String studentName);

    Optional<Student> findByRegistrationNumber(String registrationNumber);

}
