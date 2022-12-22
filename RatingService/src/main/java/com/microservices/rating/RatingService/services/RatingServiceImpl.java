package com.microservices.rating.RatingService.services;

import com.microservices.rating.RatingService.entities.Rating;
import com.microservices.rating.RatingService.exceptions.ResourceNotFoundException;
import com.microservices.rating.RatingService.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating getRatingFromID(Integer ratingID) {
        Rating rating = ratingRepository.findById(ratingID).orElseThrow(() ->
                new ResourceNotFoundException("Rating with ID - " + ratingID + " not found..."));
        return rating;
    }

    @Override
    public Boolean deleteRating(Integer ratingID) {
        ratingRepository.deleteById(ratingID);
        return true;
    }

    @Override
    public Rating modifyRating(Rating rating) {
        return ratingRepository.save(rating);
    }

}
