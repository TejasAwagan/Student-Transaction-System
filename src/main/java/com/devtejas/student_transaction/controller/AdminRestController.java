package com.devtejas.student_transaction.controller;

import com.devtejas.student_transaction.entity.ApprovedTransaction;
import com.devtejas.student_transaction.entity.StudentTransaction;
import com.devtejas.student_transaction.http.CommonHttpResponse;
import com.devtejas.student_transaction.projection.ITransactionProjection;
import com.devtejas.student_transaction.services.IAdminApproveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    @Autowired
    IAdminApproveService adminApproveService;

    @PostMapping("/approveTransaction")
    public CommonHttpResponse<List<ApprovedTransaction>> approveTransactions(@RequestBody StudentTransaction studentTransaction){
        return adminApproveService.approveTransaction(studentTransaction);
    }

    @GetMapping("/getTransactionsDetails")
    public List<ITransactionProjection> getTransactionDetails(@RequestParam String registerNo){
        return adminApproveService.getTransactionDetail(registerNo);
    }
}
