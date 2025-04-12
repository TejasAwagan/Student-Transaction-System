package com.devtejas.student_transaction.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "approved_transactions")
@Data
public class ApprovedTransaction {

    @Id
    private String id;

    private String transactionNumber;

    private String studentId;

    private Double amount;

    private String paymentMode;

    private String status;

    private Long approvedDate;
}
