package com.devtejas.student_transaction.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "student")
@Data
public class Student {

    @Id
    private String id;

    private String courseId;  // Foreign Key to Course Table

    private String studentName;

    private String registrationNumber;

}
