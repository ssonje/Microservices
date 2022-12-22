package com.microservices.rating.RatingService.services;

import com.microservices.rating.RatingService.entities.Rating;

import java.util.List;

public interface RatingService {

    Rating saveRating(Rating rating);
    List<Rating> getAllRatings();
    Rating getRatingFromID(Integer ratingID);
    Boolean deleteRating(Integer ratingID);
    Rating modifyRating(Rating rating);


}
