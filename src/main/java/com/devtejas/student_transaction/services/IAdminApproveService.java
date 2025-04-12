package com.devtejas.student_transaction.services;

import com.devtejas.student_transaction.entity.ApprovedTransaction;
import com.devtejas.student_transaction.entity.StudentTransaction;
import com.devtejas.student_transaction.http.CommonHttpResponse;
import com.devtejas.student_transaction.projection.ITransactionProjection;

import java.util.List;

public interface IAdminApproveService {

    CommonHttpResponse <List<ApprovedTransaction>> approveTransaction(StudentTransaction transaction);

    List<ITransactionProjection> getTransactionDetail(String registerNo);

}
