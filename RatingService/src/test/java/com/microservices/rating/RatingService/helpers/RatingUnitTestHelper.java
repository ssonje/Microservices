package com.microservices.rating.RatingService.helpers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import com.microservices.rating.RatingService.entities.Rating;
import com.microservices.rating.RatingService.payloads.APIResponse;
import com.microservices.rating.RatingService.repositories.RatingRepository;

public final class RatingUnitTestHelper {

    public static String TestRatingID = UUID.randomUUID().toString();
    public static String TestHotelID = "1f3ba91f-2eed-479a-a622-07cd0d571a9c";
    public static String TestUserID = "c86e615a-86ce-467e-906d-981e68b33c01";
    public static Integer TestRatingRating = 10;
    public static String TestRatingFeedback = "Very good Hotel";

    public static Rating createRatingObjectWithRatingID() {
        Rating rating = Rating.builder()
            .id(TestRatingID)
            .userID(TestUserID)
            .hotelID(TestHotelID)
            .rating(TestRatingRating)
            .feedback(TestRatingFeedback)
            .build();
        return rating;
    }

    public static Rating createRatingObjectWithoutRatingID() {
        Rating rating = Rating.builder()
            .userID(TestUserID)
            .hotelID(TestHotelID)
            .rating(TestRatingRating)
            .feedback(TestRatingFeedback)
            .build();
        return rating;
    }

    public static Rating getRatingFromOptionalRatingObject(RatingRepository ratingRepository) {
        Optional<Rating> ratingOptional = ratingRepository.findById(TestRatingID);
        return ratingOptional.get();
    }

    public static void verifyRatingDetails(Rating rating, String hotelID, String userID, Integer hotelRating, String feedback) {
        Assert.notNull(rating, "Rating get from the URL response should not be nil.");
        verifyRatingProperty(hotelID, rating.getHotelID());
        verifyRatingProperty(userID, rating.getUserID());
        verifyRatingProperty(hotelRating, rating.getRating());
        verifyRatingProperty(feedback, rating.getFeedback());
    }

    public static void verifyAPIResponse(APIResponse apiResponse, Boolean expectedResponseStatus, HttpStatus expectedHttpStatus, String expectedMessage) {
        verifyRatingProperty(expectedResponseStatus, apiResponse.getResponseStatus());
        verifyRatingProperty(expectedHttpStatus, apiResponse.getHttpStatus());
        verifyRatingProperty(expectedMessage, apiResponse.getMessage());
    }

    public static void verifyGetRatingsLength(List<Rating> ratings, List<Rating> ratingsGetFromRepository) {
        Integer ratingsLength = ratings.toArray().length;
        Integer ratingsGetFromRepositoryLegnth = ratingsGetFromRepository.toArray().length;
        verifyRatingProperty(ratingsLength, ratingsGetFromRepositoryLegnth);
    }

    public static void verifyRatingProperty(String expectedValue, String actualValue) {
        Assertions.assertEquals(expectedValue, actualValue);
    }

    public static void verifyRatingProperty(Integer expectedValue, Integer actualValue) {
        Assertions.assertEquals(expectedValue, actualValue);
    }

    public static void verifyRatingProperty(Boolean expectedValue, Boolean actualValue) {
        Assertions.assertEquals(expectedValue, actualValue);
    }

    public static void verifyRatingProperty(HttpStatus expectedValue, HttpStatus actualValue) {
        Assertions.assertEquals(expectedValue, actualValue);
    }

    public static void verifyRatingDetails(Rating rating) {
        verifyRatingDetails(rating, TestHotelID, TestUserID, TestRatingRating, TestRatingFeedback);
    }

    public static void verifyRatingDetails(List<Rating> ratings) {
        for(Rating rating: ratings) {
            verifyRatingDetails(rating, TestHotelID, TestUserID, TestRatingRating, TestRatingFeedback);
        }
    }

    public static void verifyRatingDetails(Rating rating, String feedback) {
        verifyRatingDetails(rating, TestHotelID, TestUserID, TestRatingRating, feedback);
    }

    public static void verifyRatingWithoutIDDetails(List<Rating> ratings) {
        Rating requiredRating = ratings.get(0);
        Assertions.assertEquals(ratings.toArray().length > 0, true);
        Assertions.assertNotNull(requiredRating.getId());
        Assertions.assertNotNull(requiredRating.getRating());
        Assertions.assertNotNull(requiredRating.getFeedback());
        Assertions.assertNotNull(requiredRating.getHotelID());
        Assertions.assertNotNull(requiredRating.getUserID());
    }

}
