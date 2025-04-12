package com.devtejas.student_transaction.Form;

import lombok.Data;

@Data
public class TransactionForm {
    Long date;

    Double amount;

    String referenceNumber;

    String paymentMode;
}
