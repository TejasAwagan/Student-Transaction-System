package com.devtejas.student_transaction.services;

import com.devtejas.student_transaction.entity.ApprovedTransaction;
import com.devtejas.student_transaction.entity.StudentTransaction;
import com.devtejas.student_transaction.http.CommonHttpResponse;
import com.devtejas.student_transaction.projection.ITransactionProjection;
import org.springframework.security.core.Authentication;


import java.util.List;
import java.util.Map;

public interface IAdminApproveService {

    CommonHttpResponse <List<ApprovedTransaction>> approveTransaction(StudentTransaction transaction);

    List<ITransactionProjection> getTransactionDetail(String registerNo);

    Map<String, Object> getAdminDetails(Authentication authentication);

}
