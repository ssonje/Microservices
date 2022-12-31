package com.microservices.user.UserService.controllers;

import com.microservices.user.UserService.entities.Hotel;
import com.microservices.user.UserService.entities.Rating;
import com.microservices.user.UserService.entities.User;
import com.microservices.user.UserService.external.services.HotelService;
import com.microservices.user.UserService.external.services.RatingService;
import com.microservices.user.UserService.payloads.APIResponse;
import com.microservices.user.UserService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user-service")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RatingService ratingService;

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
        User user = userService.getUserFromID(userID);

        // Call the Rating service to set the data for user
        List<Rating> ratings = ratingService.getRatingsGivenByUserWithID(user.getId()).getRatings();
        List<Rating> ratingList = ratings.stream().map(rating -> {
            // Call the Hotel service to set the data for rating
            Hotel hotel = hotelService.getHotel(rating.getHotelID()).getHotel();
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
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

}
