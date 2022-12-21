package com.microservices.hotel.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Resource NOT found on the Server...");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
