package com.microservices.rating.RatingService.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.microservices.rating.RatingService.entities.Rating;
import com.microservices.rating.RatingService.helpers.RatingUnitTestHelper;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RatingRepositoryUnitTests {

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
    void testGetRatingsFromUserID() {
        Rating ratingFromRepository = RatingUnitTestHelper.getRatingFromOptionalRatingObject(ratingRepository);
        RatingUnitTestHelper.verifyRatingDetails(ratingFromRepository);
    }

    @Test
    void testGetAllRatings() {
        Rating rating = RatingUnitTestHelper.createRatingObject();

        // Add rating to the ratings list
        List<Rating> ratings = new ArrayList<>();
        ratings.add(rating);

        RatingUnitTestHelper.verifyGetRatingsLength(ratings, ratingRepository.findAll());
    }

    @Test
    void testSaveRating() {
        Rating ratingFromRepository = RatingUnitTestHelper.getRatingFromOptionalRatingObject(ratingRepository);
        RatingUnitTestHelper.verifyRatingDetails(ratingFromRepository);
    }

    @Test
    void testDeleteRating() {
        Rating rating = RatingUnitTestHelper.createRatingObject();
    
        // Add rating to the ratings list
        List<Rating> ratings = new ArrayList<>();
        ratings.add(rating);

        // Before deleting information of one rating
        RatingUnitTestHelper.verifyGetRatingsLength(ratings, ratingRepository.findAll());

        // Remove rating from the ratings list and from the DB
        ratings.remove(0);
        ratingRepository.deleteById(RatingUnitTestHelper.TestRatingID);

        // After deleting information of one rating
        RatingUnitTestHelper.verifyGetRatingsLength(ratings, ratingRepository.findAll());
    }

    @Test
    void testModifyRating() {
        Rating rating = RatingUnitTestHelper.createRatingObject();
        ratingRepository.save(rating);

        Rating modifiedRating = RatingUnitTestHelper.getRatingFromOptionalRatingObject(ratingRepository);
        String modifyFeedback = RatingUnitTestHelper.TestRatingFeedback + " Modified";
        modifiedRating.setFeedback(modifyFeedback);
        ratingRepository.save(modifiedRating);

        // Verify the rating details
        Rating ratingFromRepository = RatingUnitTestHelper.getRatingFromOptionalRatingObject(ratingRepository);
        RatingUnitTestHelper.verifyRatingDetails(ratingFromRepository, modifyFeedback);
    }

}
