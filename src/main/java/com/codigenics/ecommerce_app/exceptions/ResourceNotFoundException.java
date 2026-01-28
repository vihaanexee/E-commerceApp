package com.codigenics.ecommerce_app.exceptions;

//resourcenotfoundexception used when the resource being search isnt availaible using 2 constructor to make it a user friendly reply
public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String field;
    String fieldName;
    Long fieldId;

    public ResourceNotFoundException(String resourceName,String field,  Long fieldId) {
        super(String.format("%s not found with %s: %s",  resourceName,field, fieldId));
        this.field = field;
        this.resourceName = resourceName;
        this.fieldId = fieldId;
    }

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String resourceName, String field, String fieldName) {
        super(String.format("%s not found with %s: %s", resourceName,field,fieldName));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;
    }
}
