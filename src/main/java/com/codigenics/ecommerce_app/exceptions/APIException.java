package com.codigenics.ecommerce_app.exceptions;

public class APIException extends RuntimeException {

    //throws an apiexception wherever logic is incorrect ie probably business logic or invalid operation or entity not found
    private final static long serialVersionUID = 1L;

    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }
}
