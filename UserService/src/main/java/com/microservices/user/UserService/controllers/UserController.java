package com.microservices.user.UserService.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.user.UserService.entities.User;
import com.microservices.user.UserService.payloads.APIResponse;
import com.microservices.user.UserService.payloads.APIResponseWithUser;
import com.microservices.user.UserService.payloads.APIResponseWithUsers;
import com.microservices.user.UserService.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/user-service")
@CircuitBreaker(name = "userRatingUserHotelBreaker", fallbackMethod = "userRatingUserHotelBreaker")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/new")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        APIResponse apiResponse;
        if (user.getId() != null) {
            apiResponse = userService.saveUserByUserID(user);
        } else {
            apiResponse = userService.saveUser(user);
        }

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<?> getUserByID(@PathVariable String userID) {
        APIResponseWithUser apiResponse = userService.getUserFromID(userID);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        APIResponseWithUsers apiResponse = userService.getAllUsers();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/user/delete/{userID}")
    public ResponseEntity<?> deleteUser(@PathVariable String userID) {
        APIResponse apiResponse = userService.deleteUser(userID);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/user/modify")
    public ResponseEntity<?> modifyUser(@RequestBody User user) {
        APIResponse apiResponse = userService.modifyUser(user);
        return ResponseEntity.ok(apiResponse);
    }

    // MARK: - Circuit breaker functions

    public ResponseEntity<?> userRatingUserHotelBreaker(Exception e) {
        APIResponse apiResponse = APIResponse.builder()
            .httpStatus(HttpStatus.BAD_REQUEST)
            .message(e.getMessage())
            .responseStatus(false)
            .build();
        APIResponseWithUsers finalResponse = APIResponseWithUsers.builder()
            .users(new ArrayList<>())
            .apiResponse(apiResponse)
            .build();
        return ResponseEntity.ok(finalResponse);
    }

}
