package com.itclopedia.cources.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class AppError extends RuntimeException {

    private int status;
    private String message;
    private Date data;

    public AppError(int status, String message) {
        this.message = message;
        this.data = new Date();
        this.status = status;
    }

}
