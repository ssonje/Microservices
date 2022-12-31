package com.microservices.rating.RatingService.controllers;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.microservices.rating.RatingService.entities.Rating;
import com.microservices.rating.RatingService.helpers.RatingUnitTestHelper;
import com.microservices.rating.RatingService.helpers.constants.RatingControllerTestAPIResponseConstants;
import com.microservices.rating.RatingService.payloads.APIResponse;
import com.microservices.rating.RatingService.payloads.APIResponseWithRatings;
import com.microservices.rating.RatingService.services.RatingService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RatingControllerUnitTest extends AbstractTest {

    @Autowired
    private RatingService ratingService;

    // MARK: - Lifecycle Methods

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        Rating rating = RatingUnitTestHelper.createRatingObjectWithRatingID();
        ratingService.saveRatingByRatingID(rating);
    }

    @AfterEach
    public void tearDown() {
        ratingService.deleteAllRatings();
    }

    // MARK: - Tests

    @Test
    public void testGetRatingWithUserID() throws Exception {
        String getRatingWithUserIDURLString = "/rating-service/ratings/user/{userID}";

        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders
                .get(getRatingWithUserIDURLString, RatingUnitTestHelper.TestUserID)
                .accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        String response = mvcResult.getResponse().getContentAsString();
        APIResponseWithRatings apiResponse = super.mapFromJson(response, APIResponseWithRatings.class);

        // Verify API response details
        RatingUnitTestHelper.verifyAPIResponse(
            apiResponse.getApiResponse(), 
            true, 
            HttpStatus.OK, 
            RatingControllerTestAPIResponseConstants.GET_ALL_RATINGS_FOR_USER_ID_SUCCESS);
    }

    @Test
    public void testGetAllRatings() throws Exception {
        String getAllRatingsURLString = "/rating-service/ratings";

        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.get(getAllRatingsURLString).accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        String response = mvcResult.getResponse().getContentAsString();
        APIResponseWithRatings apiResponse = super.mapFromJson(response, APIResponseWithRatings.class);

        // Verify API response details
        RatingUnitTestHelper.verifyAPIResponse(
            apiResponse.getApiResponse(), 
            true, 
            HttpStatus.OK, 
            RatingControllerTestAPIResponseConstants.GET_ALL_RATINGS_SUCCESS);
    }

    @Test
    public void testSaveRating() throws Exception {
        String saveRatingURLString = "/rating-service/rating/new";

        Rating rating = RatingUnitTestHelper.createRatingObjectWithRatingID();
        String inputJson = super.mapToJson(rating);

        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.post(saveRatingURLString).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
            .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        APIResponse apiResponse = super.mapFromJson(response, APIResponse.class);

        // Verify API response details
        RatingUnitTestHelper.verifyAPIResponse(apiResponse, true, HttpStatus.CREATED, RatingControllerTestAPIResponseConstants.ADD_RATING_SUCCESS);
    }

    @Test
    public void testModifyRating() throws Exception {
        String modifyRatingURLString = "/rating-service/rating/modify";

        List<Rating> modifiedratings = ratingService.getRatingsFromUserID(RatingUnitTestHelper.TestUserID).getRatings();
        Rating modifiedRating = modifiedratings.get(0);
        modifiedRating.setFeedback(RatingUnitTestHelper.TestRatingFeedback + " Modified");

        String inputJson = super.mapToJson(modifiedRating);
        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.put(modifyRatingURLString).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
            .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        APIResponse apiResponse = super.mapFromJson(response, APIResponse.class);

        // Verify API response details
        RatingUnitTestHelper.verifyAPIResponse(apiResponse, true, HttpStatus.OK, RatingControllerTestAPIResponseConstants.MODIFIED_RATING_SUCCESS);
    }

    @Test
    public void testDeleteRating() throws Exception {
        String deleteRatingURLString = "/rating-service/rating/delete/{ratingID}";

        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.delete(deleteRatingURLString, RatingUnitTestHelper.TestRatingID).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        APIResponse apiResponse = super.mapFromJson(response, APIResponse.class);

        // Verify API response details
        RatingUnitTestHelper.verifyAPIResponse(apiResponse, true, HttpStatus.OK, RatingControllerTestAPIResponseConstants.DELETE_RATING_SUCCESS);
    }

}
