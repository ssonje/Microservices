package com.microservices.user.service.controllers;

import com.microservices.user.service.entities.User;
import com.microservices.user.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/new")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<?> getUserByID(@PathVariable String userID) {
        User user = userService.getUserFromID(userID);
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
