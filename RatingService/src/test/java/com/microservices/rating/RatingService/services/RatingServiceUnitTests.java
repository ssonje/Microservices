package com.microservices.rating.RatingService.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RatingServiceUnitTests {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private RatingRepository ratingRepository;

    // MARK: - Lifecycle methods

    @BeforeEach
    void setUp() {
        Rating rating = RatingUnitTestHelper.createRatingObject();
        ratingRepository.save(rating);
    }

    @AfterEach
    void tearDown() {
        ratingRepository.deleteAll();
    }

    // MARK: - Tests

    @Test
    public void testGetRatingsByUserID() {
        List<Rating> ratingsFromService = ratingService.getRatingsFromUserID(RatingUnitTestHelper.TestUserID);
        RatingUnitTestHelper.verifyRatingDetails(ratingsFromService);
    }

    @Test
    public void testSaveRating() {
        List<Rating> ratingsFromService = ratingService.getRatingsFromUserID(RatingUnitTestHelper.TestUserID);
        RatingUnitTestHelper.verifyRatingDetails(ratingsFromService);
    }

    @Test
    public void testgetAllRatings() {
        Rating rating = RatingUnitTestHelper.createRatingObject();
    
        // Add rating to the ratings list
        List<Rating> ratings = new ArrayList<>();
        ratings.add(rating);

        RatingUnitTestHelper.verifyGetRatingsLength(ratings, ratingService.getAllRatings());
    }

    @Test
    public void testdeleteRating() {
        Rating rating = RatingUnitTestHelper.createRatingObject();
    
        // Add rating to the ratings list
        List<Rating> ratings = new ArrayList<>();
        ratings.add(rating);

        // Before deleting information of one rating
        RatingUnitTestHelper.verifyGetRatingsLength(ratings, ratingService.getAllRatings());

        // Remove rating from the ratings list and from the DB
        ratings.remove(0);
        ratingService.deleteRating(RatingUnitTestHelper.TestRatingID);

        // After deleting information of one rating
        RatingUnitTestHelper.verifyGetRatingsLength(ratings, ratingService.getAllRatings());
    }

    @Test
    public void testmodifyRating() {
        Rating modifiedRating = RatingUnitTestHelper.getRatingFromOptionalRatingObject(ratingRepository);
        String modifiedFeedback = RatingUnitTestHelper.TestRatingFeedback + " Modified";
        modifiedRating.setFeedback(modifiedFeedback);

        ratingService.modifyRating(modifiedRating);

        // Verify the rating details
        Rating ratingFromRepository = RatingUnitTestHelper.getRatingFromOptionalRatingObject(ratingRepository);
        RatingUnitTestHelper.verifyRatingDetails(ratingFromRepository, modifiedFeedback);
    }

}
