package com.microservices.user.UserService.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Resource NOT found on the Server...");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
