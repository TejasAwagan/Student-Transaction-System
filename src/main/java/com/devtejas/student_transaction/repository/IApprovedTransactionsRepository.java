package com.devtejas.student_transaction.repository;

import com.devtejas.student_transaction.entity.ApprovedTransaction;
import com.devtejas.student_transaction.projection.ITransactionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IApprovedTransactionsRepository extends JpaRepository<ApprovedTransaction,String> {
    List<ApprovedTransaction> findAllByOrderByApprovedDateDesc();

    @Query(value = "SELECT s.studentName AS studentName, " +
            "t.transactionNumber AS transactionNumber, " +
            "t.paymentMode AS paymentMode, " +
            "t.amount AS amount, " +
            "t.status AS status " +
            "FROM student s JOIN student_transaction t ON s.id = t.studentId " +
            "WHERE s.registrationNumber = :registerNo")
    List<ITransactionProjection> getTransactionDetails(@Param("registerNo") String registerNo);
}
