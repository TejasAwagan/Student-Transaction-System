package com.devtejas.student_transaction.projection;

import com.devtejas.student_transaction.entity.StudentTransaction;

import java.util.List;

public interface ITransactionProjection {
    String getStudentName();
    String getTransactionNumber();
    String getPaymentMode();
    Double getAmount();
    String getStatus();
}
