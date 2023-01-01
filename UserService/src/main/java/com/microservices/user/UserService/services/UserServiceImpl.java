package com.microservices.user.UserService.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microservices.user.UserService.constants.UserControllerAPIResponseConstants;
import com.microservices.user.UserService.entities.Rating;
import com.microservices.user.UserService.entities.User;
import com.microservices.user.UserService.exceptions.ResourceNotFoundException;
import com.microservices.user.UserService.external.services.HotelService;
import com.microservices.user.UserService.external.services.RatingService;
import com.microservices.user.UserService.payloads.APIResponse;
import com.microservices.user.UserService.payloads.APIResponseWithUser;
import com.microservices.user.UserService.payloads.APIResponseWithUsers;
import com.microservices.user.UserService.repositories.UserRepository;
import com.microservices.user.UserService.services.helpers.UserServiceImplHelper;

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

            // Save the rating and hotel object to the DB
            UserServiceImplHelper.saveRatingAndHotelForUser(user.getRatings(), ratingService, hotelService);

            // Save the hotel object to the DB
            userRepository.save(user);
            return UserServiceImplHelper.getAPIResponse(
                UserControllerAPIResponseConstants.ADD_USER_SUCCESS,
                HttpStatus.CREATED,
                true);
        } catch (Exception e) {
            return UserServiceImplHelper.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    @Override
    public APIResponseWithUsers getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            List<User> usersList = users.stream().map(user -> {
                List<Rating> ratingList = UserServiceImplHelper.getRatingsForUser(user, ratingService, hotelService);
                user.setRatings(ratingList);
                return user;
            }).collect(Collectors.toList());
            return UserServiceImplHelper.getAPIResponseForUsers(
                usersList,
                UserControllerAPIResponseConstants.GET_ALL_USERS_SUCCESS,
                HttpStatus.OK,
                true);
        } catch (Exception e) {
            return UserServiceImplHelper.getAPIResponseForUsers(new ArrayList<>() ,e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    @Override
    public APIResponseWithUser getUserFromID(String userID) {
        try {
            User user = userRepository.findById(userID).orElseThrow(() -> 
                new ResourceNotFoundException("User with ID - " + userID + " not found..."));

            List<Rating> ratingList = UserServiceImplHelper.getRatingsForUser(user, ratingService, hotelService);
            user.setRatings(ratingList);
            return UserServiceImplHelper.getAPIResponseForUser(
                user,
                UserControllerAPIResponseConstants.GET_USER_SUCCESS,
                HttpStatus.OK,
                true);
        } catch (Exception e) {
            return UserServiceImplHelper.getAPIResponseForUser(null, e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    @Override
    public APIResponse deleteUser(String userID) {
        try {
             User user = userRepository.findById(userID).orElseThrow(() -> 
                new ResourceNotFoundException("User with ID - " + userID + " not found.")
            );

            List<Rating> ratingList = UserServiceImplHelper.getRatingsForUser(user, ratingService, hotelService);
            user.setRatings(ratingList);

            // Delete the rating and hotel object from the DB
            UserServiceImplHelper.deleteRatingAndHotelForUser(user.getRatings(), ratingService, hotelService);

            // Delete user only if the user with the current userID is present
            userRepository.deleteById(userID);
            return UserServiceImplHelper.getAPIResponse(
                UserControllerAPIResponseConstants.DELETE_USER_SUCCESS,
                HttpStatus.OK,
                true);
        } catch (Exception e) {
            return UserServiceImplHelper.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    @Override
    public APIResponse modifyUser(User user) {
        try {
            String userID = user.getId();
            userRepository.findById(userID).orElseThrow(() -> 
                new ResourceNotFoundException("User with ID - " + userID + " not found.")
            );

            // Modify the rating and hotel objects which belongs to the user
            List<Rating> ratingList = UserServiceImplHelper.getRatingsForUser(user, ratingService, hotelService);
            user.setRatings(ratingList);
            UserServiceImplHelper.modifyRatingAndHotelForUser(user.getRatings(), ratingService, hotelService);

            // Modify user only if the user with the current userID is present
            userRepository.save(user);
            return UserServiceImplHelper.getAPIResponse(
                UserControllerAPIResponseConstants.MODIFIED_USER_SUCCESS,
                HttpStatus.OK,
                true);
        } catch (Exception e) {
            return UserServiceImplHelper.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    // MARK: - Helper API's

    @Override
    public APIResponse deleteAllUsers() {
        try {
            // Delete all the ratings and hotels object from the DB
            List<User> users = userRepository.findAll();
            users.stream().map(user -> {
                List<Rating> ratingList = UserServiceImplHelper.getRatingsForUser(user, ratingService, hotelService);
                user.setRatings(ratingList);
                UserServiceImplHelper.deleteRatingAndHotelForUser(user.getRatings(), ratingService, hotelService);
                return user;
            });

            userRepository.deleteAll();
            return UserServiceImplHelper.getAPIResponse(
                UserControllerAPIResponseConstants.DELETE_ALL_USERS_SUCCESS,
                HttpStatus.OK,
                true);
        } catch (Exception e) {
            return UserServiceImplHelper.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    @Override
    public APIResponse saveUserByUserID(User user) {
        try {
            if (user.getId() != null) {
                // Save the rating and hotel object to the DB
                UserServiceImplHelper.saveRatingAndHotelForUser(user.getRatings(), ratingService, hotelService);

                // save user after successfully saving rating and hotel information
                userRepository.save(user);
                return UserServiceImplHelper.getAPIResponse(
                    UserControllerAPIResponseConstants.ADD_USER_SUCCESS,
                    HttpStatus.CREATED,
                    true);
            } else {
                throw new ResourceNotFoundException("User doesn't have userID.");
            }
        } catch (Exception e) {
            return UserServiceImplHelper.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

}
