package com.microservices.rating.RatingService.controllers;

import com.microservices.rating.RatingService.entities.Rating;
import com.microservices.rating.RatingService.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/rating/new")
    public ResponseEntity<?> saveRating(@RequestBody Rating rating) {
        Rating savedRating = ratingService.saveRating(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRating);
    }

    @GetMapping("/rating/{hotelID}")
    public ResponseEntity<?> getRatingFromID(@PathVariable Integer ratingID) {
        Rating rating = ratingService.getRatingFromID(ratingID);
        return ResponseEntity.status(HttpStatus.OK).body(rating);
    }

    @GetMapping("/ratings")
    public ResponseEntity<?> getAllRatings() {
        List<Rating> ratings = ratingService.getAllRatings();
        return ResponseEntity.status(HttpStatus.OK).body(ratings);
    }

    @PostMapping("/rating/delete/{hotelID}")
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
