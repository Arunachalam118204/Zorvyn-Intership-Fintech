package com.arun.financebackend.exception;

// Thrown when a resource (user or record) is not found
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
