package com.devtejas.student_transaction.controller;

import com.devtejas.student_transaction.Form.TransactionForm;
import com.devtejas.student_transaction.entity.StudentTransaction;
import com.devtejas.student_transaction.http.CommonHttpResponse;
import com.devtejas.student_transaction.services.IStudentTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class StudentTransactionController {

    @Autowired
    private IStudentTransaction studentTransaction;

    @GetMapping("/getTransactionDetails")
    public CommonHttpResponse<StudentTransaction> getDetails(TransactionForm transactionForm){
        return studentTransaction.findTransactionReferenceNumber(transactionForm);
    }

    @PostMapping("/createTransaction")
    public CommonHttpResponse<StudentTransaction> createTransaction(@RequestBody StudentTransaction theStudentTransaction){
        return studentTransaction.createTransaction(theStudentTransaction);
    }
}
