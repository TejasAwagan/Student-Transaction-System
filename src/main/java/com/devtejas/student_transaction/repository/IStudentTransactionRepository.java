package com.devtejas.student_transaction.repository;

import com.devtejas.student_transaction.entity.StudentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface IStudentTransactionRepository extends JpaRepository<StudentTransaction, String> {

    List<StudentTransaction> findByTransactionNumber(String transactionNumber);
}
