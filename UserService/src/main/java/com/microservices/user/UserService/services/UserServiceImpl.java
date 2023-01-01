package com.microservices.user.UserService.services;

import com.microservices.user.UserService.constants.UserControllerAPIResponseConstants;
import com.microservices.user.UserService.entities.Hotel;
import com.microservices.user.UserService.entities.Rating;
import com.microservices.user.UserService.entities.User;
import com.microservices.user.UserService.exceptions.ResourceNotFoundException;
import com.microservices.user.UserService.external.services.HotelService;
import com.microservices.user.UserService.external.services.RatingService;
import com.microservices.user.UserService.payloads.APIResponse;
import com.microservices.user.UserService.payloads.APIResponseWithUser;
import com.microservices.user.UserService.payloads.APIResponseWithUsers;
import com.microservices.user.UserService.repositories.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RatingService ratingService;

    // MARK: - API's

    @Override
    public APIResponse saveUser(User user) {
        try {
            // Set the Unique ID to the Hotel id
            String randomUserId = UUID.randomUUID().toString();
            user.setId(randomUserId);

            // Save the hotel object to the DB
            userRepository.save(user);
            return UserServiceImpl.getAPIResponse(
                UserControllerAPIResponseConstants.ADD_USER_SUCCESS,
                HttpStatus.CREATED,
                true);
        } catch (Exception e) {
            return UserServiceImpl.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    @Override
    public APIResponseWithUsers getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return UserServiceImpl.getAPIResponseForUsers(
                users,
                UserControllerAPIResponseConstants.GET_ALL_USERS_SUCCESS,
                HttpStatus.OK,
                true);
        } catch (Exception e) {
            return UserServiceImpl.getAPIResponseForUsers(new ArrayList<>() ,e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    @Override
    public APIResponseWithUser getUserFromID(String userID) {
        try {
            User user = userRepository.findById(userID).orElseThrow(() -> 
                new ResourceNotFoundException("User with ID - " + userID + " not found..."));

            // Call the Rating service to set the data for user
            List<Rating> ratings = ratingService.getRatingsGivenByUserWithID(user.getId()).getRatings();
            List<Rating> ratingList = ratings.stream().map(rating -> {
                // Call the Hotel service to set the data for rating
                Hotel hotel = hotelService.getHotel(rating.getHotelID()).getHotel();
                rating.setHotel(hotel);
                return rating;
            }).collect(Collectors.toList());

            user.setRatings(ratingList);
            return UserServiceImpl.getAPIResponseForUser(
                user,
                UserControllerAPIResponseConstants.GET_USER_SUCCESS,
                HttpStatus.OK,
                true);
        } catch (Exception e) {
            return UserServiceImpl.getAPIResponseForUser(null, e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    @Override
    public APIResponse deleteUser(String userID) {
        try {
            userRepository.findById(userID).orElseThrow(() -> 
                new ResourceNotFoundException("User with ID - " + userID + " not found.")
            );

            // Delete user only if the user with the current userID is present
            userRepository.deleteById(userID);
            return UserServiceImpl.getAPIResponse(
                UserControllerAPIResponseConstants.DELETE_USER_SUCCESS,
                HttpStatus.OK,
                true);
        } catch (Exception e) {
            return UserServiceImpl.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    @Override
    public APIResponse modifyUser(User user) {
        try {
            String userID = user.getId();
            userRepository.findById(userID).orElseThrow(() -> 
                new ResourceNotFoundException("User with ID - " + userID + " not found.")
            );

            // Modify user only if the user with the current userID is present
            userRepository.save(user);
            return UserServiceImpl.getAPIResponse(
                UserControllerAPIResponseConstants.MODIFIED_USER_SUCCESS,
                HttpStatus.OK,
                true);
        } catch (Exception e) {
            return UserServiceImpl.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    // MARK: - Helper API's

    @Override
    public APIResponse deleteAllUsers() {
        try {
            userRepository.deleteAll();
            return UserServiceImpl.getAPIResponse(
                UserControllerAPIResponseConstants.DELETE_ALL_USERS_SUCCESS,
                HttpStatus.OK,
                true);
        } catch (Exception e) {
            return UserServiceImpl.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    @Override
    public APIResponse saveUserByUserID(User user) {
        try {
            if (user.getId() != null) {
                userRepository.save(user);
                return UserServiceImpl.getAPIResponse(
                    UserControllerAPIResponseConstants.ADD_USER_SUCCESS,
                    HttpStatus.CREATED,
                    true);
            } else {
                throw new ResourceNotFoundException("User doesn't have userID.");
            }
        } catch (Exception e) {
            return UserServiceImpl.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
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

    private static APIResponseWithUser getAPIResponseForUser(User user, String message, HttpStatus httpStatus, Boolean responseStatus) {
        APIResponse apiResponse = getAPIResponse(message, httpStatus, responseStatus);
        return APIResponseWithUser.builder()
            .apiResponse(apiResponse)
            .user(user)
            .build();
    }

    private static APIResponseWithUsers getAPIResponseForUsers(List<User> users, String message, HttpStatus httpStatus, Boolean responseStatus) {
        APIResponse apiResponse = getAPIResponse(message, httpStatus, responseStatus);
        return APIResponseWithUsers.builder()
            .apiResponse(apiResponse)
            .users(users)
            .build();
    }

}
