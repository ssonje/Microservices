package com.microservices.rating.RatingService.controllers;

import com.microservices.rating.RatingService.entities.Rating;
import com.microservices.rating.RatingService.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating-service")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/rating/new")
    public ResponseEntity<?> saveRating(@RequestBody Rating rating) {
        Rating savedRating = ratingService.saveRating(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRating);
    }

    @GetMapping("/ratings/user/{userID}")
    public ResponseEntity<?> getRatingsFromUserID(@PathVariable Integer userID) {
        List<Rating> rating = ratingService.getRatingsFromUserID(userID);
        return ResponseEntity.status(HttpStatus.OK).body(rating);
    }

    @GetMapping("/ratings")
    public ResponseEntity<?> getAllRatings() {
        List<Rating> ratings = ratingService.getAllRatings();
        return ResponseEntity.status(HttpStatus.OK).body(ratings);
    }

    @PostMapping("/rating/delete/{ratingID}")
    public ResponseEntity<?> deleteRating(@PathVariable Integer ratingID) {
        Boolean isRatingDeleted = ratingService.deleteRating(ratingID);
        return ResponseEntity.status(HttpStatus.CREATED).body(isRatingDeleted);
    }

    @PostMapping("/rating/modify")
    public ResponseEntity<?> modifyRating(@RequestBody Rating rating) {
        Rating modifiedRating = ratingService.modifyRating(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(modifiedRating);
    }

}
