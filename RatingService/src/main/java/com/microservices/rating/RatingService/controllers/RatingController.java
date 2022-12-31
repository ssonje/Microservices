package com.microservices.rating.RatingService.controllers;

import com.microservices.rating.RatingService.entities.Rating;
import com.microservices.rating.RatingService.payloads.APIResponse;
import com.microservices.rating.RatingService.payloads.APIResponseWithRatings;
import com.microservices.rating.RatingService.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        APIResponseWithRatings apiResponse = ratingService.getRatingsFromUserID(userID);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/ratings")
    public ResponseEntity<?> getAllRatings() {
        APIResponseWithRatings apiResponse = ratingService.getAllRatings();
        return ResponseEntity.ok(apiResponse);
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
