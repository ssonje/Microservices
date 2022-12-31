package com.microservices.rating.RatingService.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.microservices.rating.RatingService.entities.Rating;
import com.microservices.rating.RatingService.helpers.RatingUnitTestHelper;
import com.microservices.rating.RatingService.repositories.RatingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RatingServiceUnitTests {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private RatingRepository ratingRepository;

    // MARK: - Lifecycle methods

    @AfterEach
    void tearDown() {
        ratingRepository.deleteAll();
    }

    // MARK: - Tests

    @Test
    public void testGetRatingsByUserID() {
        createAndSaveRatingWithID(ratingRepository);
        List<Rating> ratingsFromService = ratingService.getRatingsFromUserID(RatingUnitTestHelper.TestUserID).getRatings();
        RatingUnitTestHelper.verifyRatingDetails(ratingsFromService);
    }

    @Test
    public void testSaveRatingWithID() {
        createAndSaveRatingWithID(ratingRepository);
        List<Rating> ratingsFromService = ratingService.getRatingsFromUserID(RatingUnitTestHelper.TestUserID).getRatings();
        RatingUnitTestHelper.verifyRatingDetails(ratingsFromService);
    }

    @Test
    public void testSaveRatingWithoutID() {
        createAndSaveRatingWithoutID(ratingRepository);
        List<Rating> ratings = ratingRepository.findAll();
        RatingUnitTestHelper.verifyRatingWithoutIDDetails(ratings);
    }

    @Test
    public void testgetAllRatings() {
        createAndSaveRatingWithID(ratingRepository);
        Rating rating = RatingUnitTestHelper.createRatingObjectWithRatingID();
    
        // Add rating to the ratings list
        List<Rating> ratings = new ArrayList<>();
        ratings.add(rating);

        RatingUnitTestHelper.verifyGetRatingsLength(ratings, ratingService.getAllRatings().getRatings());
    }

    @Test
    public void testdeleteRating() {
        createAndSaveRatingWithID(ratingRepository);
        Rating rating = RatingUnitTestHelper.createRatingObjectWithRatingID();
    
        // Add rating to the ratings list
        List<Rating> ratings = new ArrayList<>();
        ratings.add(rating);

        // Before deleting information of one rating
        RatingUnitTestHelper.verifyGetRatingsLength(ratings, ratingService.getAllRatings().getRatings());

        // Remove rating from the ratings list and from the DB
        ratings.remove(0);
        ratingService.deleteRating(RatingUnitTestHelper.TestRatingID);

        // After deleting information of one rating
        RatingUnitTestHelper.verifyGetRatingsLength(ratings, ratingService.getAllRatings().getRatings());
    }

    @Test
    public void testmodifyRating() {
        createAndSaveRatingWithID(ratingRepository);
        Rating modifiedRating = RatingUnitTestHelper.getRatingFromOptionalRatingObject(ratingRepository);
        String modifiedFeedback = RatingUnitTestHelper.TestRatingFeedback + " Modified";
        modifiedRating.setFeedback(modifiedFeedback);

        ratingService.modifyRating(modifiedRating);

        // Verify the rating details
        Rating ratingFromRepository = RatingUnitTestHelper.getRatingFromOptionalRatingObject(ratingRepository);
        RatingUnitTestHelper.verifyRatingDetails(ratingFromRepository, modifiedFeedback);
    }

    // MARK: - Helper functions

    private static void createAndSaveRatingWithID(RatingRepository ratingRepository) {
        Rating ratingWithID = RatingUnitTestHelper.createRatingObjectWithRatingID();
        ratingRepository.save(ratingWithID);
    }

    private static void createAndSaveRatingWithoutID(RatingRepository ratingRepository) {
        Rating ratingWithoutID = RatingUnitTestHelper.createRatingObjectWithoutRatingID();
        ratingWithoutID.setId(UUID.randomUUID().toString());
        ratingRepository.save(ratingWithoutID);
    }

}
