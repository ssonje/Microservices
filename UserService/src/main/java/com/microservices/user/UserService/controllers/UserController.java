package com.microservices.user.UserService.controllers;

import com.microservices.user.UserService.entities.Hotel;
import com.microservices.user.UserService.entities.Rating;
import com.microservices.user.UserService.entities.User;
import com.microservices.user.UserService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    private static String RatingServiceBaseURL = "http://RATING-SERVICE/";
    private static String HotelServiceBaseURL = "http://HOTEL-SERVICE/";

    @PostMapping("/user/new")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<?> getUserByID(@PathVariable String userID) {
        User user = userService.getUserFromID(userID);

        // Call the Rating service to set the data for user
        Rating[] ratingsForUserID = restTemplate.getForObject(
                UserController.RatingServiceBaseURL + "ratings/user/" + user.getId(),
                Rating[].class);
        List<Rating> ratings = Arrays.stream(ratingsForUserID).toList();
        List<Rating> ratingList = ratings.stream().map(rating -> {
            // Call the Hotel service to set the data for rating
            ResponseEntity<Hotel> hotelResponse = restTemplate.getForEntity(
                    UserController.HotelServiceBaseURL + "hotel/" + rating.getHotelID(),
                    Hotel.class);
            Hotel hotel = hotelResponse.getBody();
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
