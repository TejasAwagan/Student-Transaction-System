package com.devtejas.student_transaction.http;

import lombok.Data;

@Data
public class CommonHttpResponse<TEntity> {
    private String message;
    private Boolean success;
    private Integer responseCode;
    private TEntity data;
}
