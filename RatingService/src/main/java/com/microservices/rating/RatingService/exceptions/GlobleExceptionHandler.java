package com.microservices.rating.RatingService.exceptions;

import com.microservices.rating.RatingService.payloads.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobleExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        APIResponse apiResponse = APIResponse.builder()
                .message(exception.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND)
                .responseStatus(false)
                .build();
        return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

}
