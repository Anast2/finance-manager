package com.itclopedia.cources.exceptions;

public class InvalidArgumentException extends RuntimeException {

    public InvalidArgumentException(String message) {
        super(message);
    }

    public InvalidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

}
