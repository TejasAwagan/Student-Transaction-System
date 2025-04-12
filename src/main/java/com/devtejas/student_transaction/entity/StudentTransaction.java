package com.devtejas.student_transaction.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
@Entity(name = "student_transaction")
public class StudentTransaction {

    @Id
    private String id;

    private String studentId;

    private String transactionNumber;

    private String paymentMode;

    private Double amount;

    private String status;

    private Long transactionDate;

    private Long creationDate;

    private Long modificationDate;

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = "PENDING";  // Default value set before saving
        }
    }
}
