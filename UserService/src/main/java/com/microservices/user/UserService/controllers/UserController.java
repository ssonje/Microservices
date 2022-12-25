package com.microservices.user.UserService.controllers;

import com.microservices.user.UserService.entities.Rating;
import com.microservices.user.UserService.entities.User;
import com.microservices.user.UserService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    private static String RatingServiceBaseURL = "http://localhost:8083/";

    @PostMapping("/user/new")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<?> getUserByID(@PathVariable String userID) {
        User user = userService.getUserFromID(userID);
        ArrayList<Rating> ratingsForUserID = restTemplate.getForObject(
                UserController.RatingServiceBaseURL + "ratings/user/" + user.getId(),
                ArrayList.class);
        user.setRatings(ratingsForUserID);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping("/user/delete/{userID}")
    public ResponseEntity<?> deleteUser(@PathVariable String userID) {
        Boolean isUserDeleted = userService.deleteUser(userID);
        return ResponseEntity.status(HttpStatus.CREATED).body(isUserDeleted);
    }

    @PostMapping("/user/modify")
    public ResponseEntity<?> modifyUser(@RequestBody User user) {
        User modifiedUser = userService.modifyUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(modifiedUser);
    }

}