package com.devtejas.student_transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class StudentTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentTransactionApplication.class, args);
	}

}
