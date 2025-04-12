package com.devtejas.student_transaction.services;

import com.devtejas.student_transaction.Form.TransactionForm;
import com.devtejas.student_transaction.entity.StudentTransaction;
import com.devtejas.student_transaction.http.CommonHttpResponse;

public interface IStudentTransaction {
    CommonHttpResponse<StudentTransaction> findTransactionReferenceNumber(TransactionForm transactionForm);

    CommonHttpResponse<StudentTransaction> getByStudentId(String id);

    CommonHttpResponse<StudentTransaction> getByDate(Long date);

    CommonHttpResponse<StudentTransaction> updateTranactionStatus(StudentTransaction status);

    CommonHttpResponse<StudentTransaction> createTransaction(StudentTransaction studentTransaction);
}
