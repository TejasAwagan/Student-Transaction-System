package com.devtejas.student_transaction.repository;

import com.devtejas.student_transaction.entity.StudentFees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentFeesRepository extends JpaRepository<StudentFees, String> {

    StudentFees findByStudentId(String studentId);
}
