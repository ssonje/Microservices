package com.microservices.rating.RatingService.services;

import com.microservices.rating.RatingService.entities.Rating;
import com.microservices.rating.RatingService.payloads.APIResponse;

import java.util.List;

public interface RatingService {

    APIResponse saveRating(Rating rating);
    List<Rating> getAllRatings();
    List<Rating> getRatingsFromUserID(String userID);
    APIResponse deleteRating(String ratingID);
    APIResponse modifyRating(Rating rating);
    APIResponse deleteAllRatings();
    APIResponse saveRatingByRatingID(Rating rating);

}
