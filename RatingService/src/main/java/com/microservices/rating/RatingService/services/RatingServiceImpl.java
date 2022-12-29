package com.microservices.rating.RatingService.services;

import com.microservices.rating.RatingService.constants.RatingControllerAPIResponseConstants;
import com.microservices.rating.RatingService.entities.Rating;
import com.microservices.rating.RatingService.payloads.APIResponse;
import com.microservices.rating.RatingService.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    // MARK: - API's

    @Override
    public APIResponse saveRating(Rating rating) {
        try {
            String randomUserId = UUID.randomUUID().toString();
            rating.setId(randomUserId);
            ratingRepository.save(rating);
            return RatingServiceImpl.getAPIResponse(
                RatingControllerAPIResponseConstants.ADD_RATING_SUCCESS,
                HttpStatus.CREATED,
                true);
        } catch (Exception e) {
            return RatingServiceImpl.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    @Override
    public APIResponse saveRatingByRatingID(Rating rating) {
        try {
            ratingRepository.save(rating);
            return RatingServiceImpl.getAPIResponse(
                RatingControllerAPIResponseConstants.ADD_RATING_SUCCESS,
                HttpStatus.CREATED,
                true);
        } catch (Exception e) {
            return RatingServiceImpl.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public List<Rating> getRatingsFromUserID(String userID) {
        try {
            List<Rating> rating = ratingRepository.findByUserID(userID);
            return rating;
        } catch(Exception e) {
            List<Rating> rating = new ArrayList<>();
            return rating;
        }
        
    }

    @Override
    public APIResponse deleteRating(String ratingID) {
        try {
            ratingRepository.deleteById(ratingID);
            return RatingServiceImpl.getAPIResponse(
                RatingControllerAPIResponseConstants.DELETE_RATING_SUCCESS,
                HttpStatus.OK,
                true);
        } catch (Exception e) {
            return RatingServiceImpl.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    @Override
    public APIResponse deleteAllRatings() {
        try {
            ratingRepository.deleteAll();
            return RatingServiceImpl.getAPIResponse(
                RatingControllerAPIResponseConstants.DELETE_RATING_SUCCESS,
                HttpStatus.OK,
                true);
        } catch (Exception e) {
            return RatingServiceImpl.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    @Override
    public APIResponse modifyRating(Rating rating) {
        try {
            ratingRepository.save(rating);
            return RatingServiceImpl.getAPIResponse(
                RatingControllerAPIResponseConstants.MODIFIED_RATING_SUCCESS,
                HttpStatus.OK,
                true);
        } catch (Exception e) {
            return RatingServiceImpl.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    // MARK: - Private helper methods

    private static APIResponse getAPIResponse(String message, HttpStatus httpStatus, Boolean responseStatus) {
        return APIResponse.builder()
        .message(message)
        .httpStatus(httpStatus)
        .responseStatus(responseStatus)
        .build();
    }

}
