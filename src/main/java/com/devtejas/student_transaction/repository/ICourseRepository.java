package com.devtejas.student_transaction.repository;

import com.devtejas.student_transaction.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ICourseRepository extends JpaRepository<Course, String> {
    Optional<Course> findByName(String name);

}
