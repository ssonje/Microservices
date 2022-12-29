package com.microservices.rating.RatingService.controllers;

import com.microservices.rating.RatingService.entities.Rating;
import com.microservices.rating.RatingService.payloads.APIResponse;
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
        APIResponse apiResponse;
        if (rating.getId() != null) {
            apiResponse = ratingService.saveRatingByRatingID(rating);
        } else {
            apiResponse = ratingService.saveRating(rating);
        }

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/ratings/user/{userID}")
    public ResponseEntity<?> getRatingsFromUserID(@PathVariable String userID) {
        List<Rating> rating = ratingService.getRatingsFromUserID(userID);
        return ResponseEntity.status(HttpStatus.OK).body(rating);
    }

    @GetMapping("/ratings")
    public ResponseEntity<?> getAllRatings() {
        List<Rating> ratings = ratingService.getAllRatings();
        return ResponseEntity.status(HttpStatus.OK).body(ratings);
    }

    @DeleteMapping("/rating/delete/{ratingID}")
    public ResponseEntity<?> deleteRating(@PathVariable String ratingID) {
        APIResponse apiResponse = ratingService.deleteRating(ratingID);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/rating/modify")
    public ResponseEntity<?> modifyRating(@RequestBody Rating rating) {
        APIResponse apiResponse = ratingService.modifyRating(rating);
        return ResponseEntity.ok(apiResponse);
    }

}
