package com.devtejas.student_transaction.services;

import com.devtejas.student_transaction.Form.TransactionForm;
import com.devtejas.student_transaction.entity.StudentTransaction;
import com.devtejas.student_transaction.globalexceptions.BadRequestException;
import com.devtejas.student_transaction.globalexceptions.ConflictException;
import com.devtejas.student_transaction.http.CommonHttpResponse;
import com.devtejas.student_transaction.repository.IStudentTransactionRepository;
import jakarta.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
public class StudentTransactionImpl implements IStudentTransaction{

    @Autowired
    private IStudentTransactionRepository studentTransactionRepository;

    @Override
    public CommonHttpResponse<StudentTransaction> findTransactionReferenceNumber(TransactionForm transactionForm) {
        var response = new CommonHttpResponse<StudentTransaction>();

        Optional<StudentTransaction> existTran = studentTransactionRepository.findById(transactionForm.getReferenceNumber());

        if(!existTran.isPresent()){
            response.setSuccess(false);
            response.setResponseCode(404);
            response.setMessage("Transaction with id " + transactionForm.getReferenceNumber() + "not found");
            return response;
        }

        StudentTransaction transaction = existTran.get();

        response.setResponseCode(200);
        response.setSuccess(true);
        response.setMessage("Transaction Found Successfully !");
        response.setData(transaction);
        return response;
    }

    @Override
    public CommonHttpResponse<StudentTransaction> getByStudentId(String id) {
        return null;
    }

    @Override
    public CommonHttpResponse<StudentTransaction> getByDate(Long date) {
        return null;
    }

    @Override
    public CommonHttpResponse<StudentTransaction> updateTranactionStatus(StudentTransaction status) {
        return null;
    }

    @Override
    public CommonHttpResponse<StudentTransaction> createTransaction(StudentTransaction studentTransaction) {
        var response = new CommonHttpResponse<StudentTransaction>();

        if (studentTransaction == null) {
            throw new BadRequestException("Transaction details cannot be null.");
        }

        List<StudentTransaction> existTransaction  = studentTransactionRepository.findByTransactionNumber(studentTransaction.getTransactionNumber());

        if(existTransaction.isEmpty()){
            throw new ConflictException("Transaction with transaction number " + studentTransaction.getTransactionNumber() + " already exists!");
        }

        StudentTransaction transaction = new StudentTransaction();

        transaction.setId(UUID.randomUUID().toString());
        transaction.setStudentId(studentTransaction.getStudentId());
        transaction.setTransactionNumber(studentTransaction.getTransactionNumber());
        transaction.setPaymentMode(studentTransaction.getPaymentMode());
        transaction.setAmount(studentTransaction.getAmount());
        transaction.setTransactionDate(System.currentTimeMillis());
        transaction.setCreationDate(System.currentTimeMillis());

        StudentTransaction savedTransaction  = studentTransactionRepository.save(transaction);

        response.setSuccess(true);
        response.setResponseCode(200);
        response.setMessage("Transaction created Successfully !");
        response.setData(savedTransaction);

        return response;

    }
}




















