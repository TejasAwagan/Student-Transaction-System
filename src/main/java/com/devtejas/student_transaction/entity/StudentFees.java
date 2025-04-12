package com.devtejas.student_transaction.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity(name = "student_fees")
@Data
public class StudentFees {

    @Id
    private String id;

    private String studentId;  // Foreign Key to Student Table

    private Double totalFees;

    private Double paidFees;

    private Double dueAmount;

    private String paymentStatus;

}
