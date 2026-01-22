package com.codigenics.ecommerce_app.exceptions;

public class APIException extends RuntimeException {
    private final static long serialVersionUID = 1L;

    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }
}
