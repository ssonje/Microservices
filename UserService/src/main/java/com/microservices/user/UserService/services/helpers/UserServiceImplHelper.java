package com.microservices.user.UserService.services.helpers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;

import com.microservices.user.UserService.entities.Hotel;
import com.microservices.user.UserService.entities.Rating;
import com.microservices.user.UserService.entities.User;
import com.microservices.user.UserService.external.services.HotelService;
import com.microservices.user.UserService.external.services.RatingService;
import com.microservices.user.UserService.payloads.APIResponse;
import com.microservices.user.UserService.payloads.APIResponseWithUser;
import com.microservices.user.UserService.payloads.APIResponseWithUsers;

public final class UserServiceImplHelper {

    public static APIResponse getAPIResponse(String message, HttpStatus httpStatus, Boolean responseStatus) {
        return APIResponse.builder()
                .message(message)
                .httpStatus(httpStatus)
                .responseStatus(responseStatus)
                .build();
    }

    public static APIResponseWithUser getAPIResponseForUser(User user, String message, HttpStatus httpStatus, Boolean responseStatus) {
        APIResponse apiResponse = getAPIResponse(message, httpStatus, responseStatus);
        return APIResponseWithUser.builder()
            .apiResponse(apiResponse)
            .user(user)
            .build();
    }

    public static APIResponseWithUsers getAPIResponseForUsers(List<User> users, String message, HttpStatus httpStatus, Boolean responseStatus) {
        APIResponse apiResponse = getAPIResponse(message, httpStatus, responseStatus);
        return APIResponseWithUsers.builder()
            .apiResponse(apiResponse)
            .users(users)
            .build();
    }

    public static List<Rating> getRatingsForUser(User user, RatingService ratingService, HotelService hotelService) {
        // Call the Rating service to set the data for user
        List<Rating> ratings = ratingService.getRatingsGivenByUserWithID(user.getId()).getRatings();
        List<Rating> ratingList = ratings.stream().map(rating -> {
            // Call the Hotel service to set the data for rating
            Hotel hotel = hotelService.getHotelFromID(rating.getHotelID()).getHotel();
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        return ratingList;
    }

    public static void saveRatingAndHotelForUser(List<Rating> ratings, RatingService ratingService, HotelService hotelService) {
        // Call the Rating service to modify the rating get from user
        ratings.stream().map(rating -> {
            // Call the hotel service to set the data for user
            hotelService.saveHotel(rating.getHotel());
            ratingService.saveRating(rating);
            return rating;
        }).collect(Collectors.toList());
    }

    public static void modifyRatingAndHotelForUser(List<Rating> ratings, RatingService ratingService, HotelService hotelService) {
        // Call the Rating service to modify the rating get from user
        ratings.stream().map(rating -> {
            // Call the hotel service to set the data for user
            hotelService.modifyHotel(rating.getHotel());
            ratingService.modifyRatingsGivenByUserWithID(rating);
            return rating;
        }).collect(Collectors.toList());
    }

    public static void deleteRatingAndHotelForUser(List<Rating> ratings, RatingService ratingService, HotelService hotelService) {
        // Call the Rating service to modify the rating get from user
        ratings.stream().map(rating -> {
            // Call the hotel service to set the data for user
            hotelService.deleteHotel(rating.getHotel().getId());
            ratingService.deleteRating(rating.getId());
            return rating;
        }).collect(Collectors.toList());
    }

}
