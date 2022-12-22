package com.microservices.rating.RatingService.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Resource NOT found on the Server...");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
