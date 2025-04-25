package com.devtejas.student_transaction.services;

import com.devtejas.student_transaction.entity.ApprovedTransaction;
import com.devtejas.student_transaction.entity.StudentFees;
import com.devtejas.student_transaction.entity.StudentTransaction;
import com.devtejas.student_transaction.http.CommonHttpResponse;
import com.devtejas.student_transaction.projection.ITransactionProjection;
import com.devtejas.student_transaction.repository.IApprovedTransactionsRepository;
import com.devtejas.student_transaction.repository.IStudentFeesRepository;
import com.devtejas.student_transaction.repository.IStudentTransactionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ApproveServiceImpl implements IAdminApproveService{
    

    @Autowired
    IStudentTransactionRepository studentTransactionRepository;

    @Autowired
    IStudentFeesRepository studentFeesRepository;

    @Autowired
    IApprovedTransactionsRepository approvedTransactionRepository;

    @Override
    public CommonHttpResponse<List<ApprovedTransaction>> approveTransaction(StudentTransaction transaction) {
        var response = new CommonHttpResponse<List<ApprovedTransaction>>();

        List<StudentTransaction> optionalTransaction = studentTransactionRepository.findByTransactionNumber(transaction.getTransactionNumber());

        if (optionalTransaction.isEmpty()) {
            response.setSuccess(false);
            response.setResponseCode(404);
            response.setMessage("Transaction not found!");
            return response;
        }

        StudentTransaction existingTransaction = optionalTransaction.get(0);

        // Check if transaction is already approved or rejected
        if (!"PENDING".equals(existingTransaction.getStatus())) {
            response.setSuccess(false);
            response.setResponseCode(400);
            response.setMessage("Transaction is already processed!");
            return response;
        }

        // Validate that the status provided is either COMPLETED or REJECTED
        if (!"COMPLETED".equalsIgnoreCase(transaction.getStatus()) &&
                !"REJECTED".equalsIgnoreCase(transaction.getStatus())) {
            response.setSuccess(false);
            response.setResponseCode(400);
            response.setMessage("Invalid status! Use 'COMPLETED' or 'REJECTED' only.");
            return response;
        }

        // If approved, update StudentFees table first
        if ("COMPLETED".equalsIgnoreCase(transaction.getStatus())) {
            StudentFees studentFees = studentFeesRepository.findByStudentId(existingTransaction.getStudentId());

            if (studentFees == null) {
                response.setSuccess(false);
                response.setResponseCode(404);
                response.setMessage("Student fees record not found!");
                return response;
            }

            // Reflect the transaction amount in dueAmount field
            studentFees.setDueAmount(studentFees.getDueAmount() - existingTransaction.getAmount());
//            studentFees.getPaidFees(transaction.getAmount());

            // Update paymentStatus based on the due amount
            if (studentFees.getDueAmount() <= 0) {
                studentFees.setPaymentStatus("PAID");
                studentFees.setDueAmount(0.0);  // Ensure dueAmount is not negative
            } else {
                studentFees.setPaymentStatus("PARTIALLY PAID");
            }

            StudentFees save = studentFeesRepository.save(studentFees);// Save StudentFees first
        }

        // Now update the transaction status
        existingTransaction.setStatus(transaction.getStatus());
        existingTransaction.setModificationDate(System.currentTimeMillis());
        studentTransactionRepository.save(existingTransaction); // Save transaction status update

        // If approved, store it in ApprovedTransaction table
        if ("COMPLETED".equalsIgnoreCase(transaction.getStatus())) {
            ApprovedTransaction approvedTransaction = new ApprovedTransaction();

            approvedTransaction.setId(UUID.randomUUID().toString());
            approvedTransaction.setTransactionNumber(existingTransaction.getTransactionNumber());
            approvedTransaction.setStudentId(existingTransaction.getStudentId());
            approvedTransaction.setAmount(existingTransaction.getAmount());
            approvedTransaction.setPaymentMode(existingTransaction.getPaymentMode());
            approvedTransaction.setStatus("COMPLETED");
            approvedTransaction.setApprovedDate(System.currentTimeMillis());

            approvedTransactionRepository.save(approvedTransaction);
        }

        // Fetch updated approved transactions list
        List<ApprovedTransaction> approvedTransactions = approvedTransactionRepository.findAllByOrderByApprovedDateDesc();

        response.setSuccess(true);
        response.setResponseCode(200);
        response.setMessage("Transaction processed successfully!");
        response.setData(approvedTransactions);

        return response;
    }

    @Override
    public List<ITransactionProjection> getTransactionDetail(String registerNo) {
//        var response = new CommonHttpResponse<List<ITransactionProjection>>();

        var result= approvedTransactionRepository.getTransactionDetails(registerNo);
        return result;

    }

    @Override
    public Map<String, Object> getAdminDetails(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();

        String username = jwt.getClaimAsString("preferred_username");
        String email = jwt.getClaimAsString("email");

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Map<String, Object> details = new HashMap<>();
        details.put("username", username);
        details.put("email", email);
        details.put("roles", roles);

        return details;
    }


}
