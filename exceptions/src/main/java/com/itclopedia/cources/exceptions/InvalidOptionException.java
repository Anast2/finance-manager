package com.itclopedia.cources.exceptions;

public class InvalidOptionException extends RuntimeException {

    public InvalidOptionException(String message) {
        super(message);
    }

    public InvalidOptionException(String message, Throwable cause) {
        super(message, cause);
    }

}