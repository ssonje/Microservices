package com.microservices.rating.RatingService.services;

import com.microservices.rating.RatingService.entities.Rating;
import com.microservices.rating.RatingService.payloads.APIResponse;
import com.microservices.rating.RatingService.payloads.APIResponseWithRatings;

public interface RatingService {

    APIResponse saveRating(Rating rating);
    APIResponseWithRatings getAllRatings();
    APIResponseWithRatings getRatingsFromUserID(String userID);
    APIResponse deleteRating(String ratingID);
    APIResponse modifyRating(Rating rating);
    APIResponse deleteAllRatings();
    APIResponse saveRatingByRatingID(Rating rating);

}
