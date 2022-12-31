package com.microservices.user.UserService.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import com.microservices.user.UserService.entities.Hotel;
import com.microservices.user.UserService.entities.Rating;
import com.microservices.user.UserService.entities.User;
import com.microservices.user.UserService.payloads.APIResponse;
import com.microservices.user.UserService.repositories.UserRepository;

public final class UserUnitTestHelper {

    public static String TestUserID = UUID.randomUUID().toString();
    public static String TestRatingID = UUID.randomUUID().toString();
    public static String TestHotelID = UUID.randomUUID().toString();
    public static String TestUserName = "Test User Name";
    public static String TestUserAbout = "Test User About";
    public static String TestUserEmail = "Test@gmail.com";
    public static Integer TestRatingRating = 10;
    public static String TestRatingFeedback = "Very good Hotel";
    public static String TestHotelName = "Test Hotel Name";
    public static String TestHotelAbout = "Test Hotel About";
    public static String TestHotelLocation = "Test Hotel Location";

    public static User createUserObjectWithUserID() {
        List<Rating> ratings = new ArrayList<>();
        Hotel hotel = UserUnitTestHelper.createHotelObject();
        Rating rating = UserUnitTestHelper.createRatingObject();
        rating.setHotel(hotel);
        ratings.add(rating);
        return User.builder()
            .id(TestUserID)
            .name(TestUserName)
            .about(TestUserAbout)
            .email(TestUserEmail)
            .ratings(ratings)
            .build();
    }

    public static User createUserObjectWithoutUserID() {
        List<Rating> ratings = new ArrayList<>();
        Hotel hotel = UserUnitTestHelper.createHotelObject();
        Rating rating = UserUnitTestHelper.createRatingObject();
        rating.setHotel(hotel);
        ratings.add(rating);
        return User.builder()
            .id(UUID.randomUUID().toString())
            .name(TestUserName)
            .about(TestUserAbout)
            .email(TestUserEmail)
            .ratings(ratings)
            .build();
    }

    public static User getUserFromOptionalUserObject(UserRepository userRepository) {
        Optional<User> userOptional = userRepository.findById(TestUserID);
        return userOptional.get();
    }

    public static void verifyUserDetails(User user, String userName, String userAbout, String userEmail) {
        Assert.notNull(user, "User get from the response should not be nil.");
        Assertions.assertEquals(userName, user.getName());
        Assertions.assertEquals(userAbout, user.getAbout());
        Assertions.assertEquals(userEmail, user.getEmail());
    }

    public static void verifyUserDetails(User user) {
        verifyUserDetails(user, TestUserName, TestUserAbout, TestUserEmail);
    }

    public static void verifyUserDetails(User user, String userName) {
        verifyUserDetails(user, userName, TestUserAbout, TestUserEmail);
    }

    public static void verifyAPIResponse(APIResponse apiResponse,
        Boolean expectedResponseStatus,
        HttpStatus expectedHttpStatus,
        String expectedMessage) {
        Assertions.assertEquals(expectedResponseStatus, apiResponse.getResponseStatus());
        Assertions.assertEquals(expectedHttpStatus, apiResponse.getHttpStatus());
        Assertions.assertEquals(expectedMessage, apiResponse.getMessage());
    }

    public static void verifyGetUsersLength(List<User> users, List<User> usersGetFromRepository) {
        Integer usersLength = users.toArray().length;
        Integer usersGetFromRepositoryLegnth = usersGetFromRepository.toArray().length;
        Assertions.assertEquals(usersLength, usersGetFromRepositoryLegnth);
    }

    // MARK: - Private Helpers

    private static Rating createRatingObject() {
        Rating rating = Rating.builder()
            .id(TestRatingID)
            .userID(TestUserID)
            .hotelID(TestHotelID)
            .rating(TestRatingRating)
            .feedback(TestRatingFeedback)
            .build();
        return rating;
    }

    private static Hotel createHotelObject() {
        Hotel hotel = Hotel.builder()
            .id(TestHotelID)
            .name(TestHotelName)
            .about(TestHotelAbout)
            .location(TestHotelLocation)
            .build();
        return hotel;
    }

    public static void verifyUserWithoutIDDetails(List<User> users) {
        User user = users.get(0);
        Assertions.assertEquals(users.toArray().length > 0, true);
        Assertions.assertNotNull(user.getId());
        Assertions.assertNotNull(user.getAbout());
        Assertions.assertNotNull(user.getEmail());
        Assertions.assertNotNull(user.getName());
    }

}
