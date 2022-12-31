package com.microservices.rating.RatingService.services;

import com.microservices.rating.RatingService.constants.RatingControllerAPIResponseConstants;
import com.microservices.rating.RatingService.entities.Rating;
import com.microservices.rating.RatingService.exceptions.ResourceNotFoundException;
import com.microservices.rating.RatingService.payloads.APIResponse;
import com.microservices.rating.RatingService.payloads.APIResponseWithRatings;
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
            // Set the Unique ID to the Hotel id
            String randomUserId = UUID.randomUUID().toString();
            rating.setId(randomUserId);

            // Save the hotel object to the DB
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
    public APIResponseWithRatings getAllRatings() {
        try {
            List<Rating> ratings = ratingRepository.findAll();
            return RatingServiceImpl.getAPIResponseForRatings(
                ratings,
                RatingControllerAPIResponseConstants.GET_ALL_RATINGS_SUCCESS,
                HttpStatus.OK,
                true);
        } catch (Exception e) {
            return RatingServiceImpl.getAPIResponseForRatings(new ArrayList<>(), e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    @Override
    public APIResponseWithRatings getRatingsFromUserID(String userID) {
        try {
            List<Rating> ratings = ratingRepository.findByUserID(userID);
            return RatingServiceImpl.getAPIResponseForRatings(
                ratings,
                RatingControllerAPIResponseConstants.GET_ALL_RATINGS_FOR_USER_ID_SUCCESS,
                HttpStatus.OK,
                true);
        } catch (Exception e) {
            return RatingServiceImpl.getAPIResponseForRatings(new ArrayList<>(), e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    @Override
    public APIResponse deleteRating(String ratingID) {
        try {
            ratingRepository.findById(ratingID).orElseThrow(() ->
                new ResourceNotFoundException("Rating with ID - " + ratingID + " not found."));

            // Delete rating only if the rating with the current ratingID is present
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
    public APIResponse modifyRating(Rating rating) {
        try {
            String ratingID = rating.getId();
            ratingRepository.findById(ratingID).orElseThrow(() ->
                new ResourceNotFoundException("Rating with ID - " + ratingID + " not found."));

            // Modify rating only if the rating with the current ratingID is present
            ratingRepository.save(rating);
            return RatingServiceImpl.getAPIResponse(
                RatingControllerAPIResponseConstants.MODIFIED_RATING_SUCCESS,
                HttpStatus.OK,
                true);
        } catch (Exception e) {
            return RatingServiceImpl.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    // MARK: - Helper API's

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
    public APIResponse saveRatingByRatingID(Rating rating) {
        try {
            if (rating.getId() != null) {
                ratingRepository.save(rating);
                return RatingServiceImpl.getAPIResponse(
                    RatingControllerAPIResponseConstants.ADD_RATING_SUCCESS,
                    HttpStatus.CREATED,
                    true);
            } else {
                throw new ResourceNotFoundException("Rating doesn't have ratingID.");
            }
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

    private static APIResponseWithRatings getAPIResponseForRatings(List<Rating> ratings, String message, HttpStatus httpStatus, Boolean responseStatus) {
        APIResponse apiResponse = getAPIResponse(message, httpStatus, responseStatus);
        return APIResponseWithRatings.builder()
            .apiResponse(apiResponse)
            .ratings(ratings)
            .build();
    }

}
