package com.microservices.user.service.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Resource NOT found on the Server...");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
