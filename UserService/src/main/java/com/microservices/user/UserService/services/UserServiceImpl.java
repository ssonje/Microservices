package com.microservices.user.UserService.services;

import com.microservices.user.UserService.constants.UserControllerAPIResponseConstants;
import com.microservices.user.UserService.entities.User;
import com.microservices.user.UserService.exceptions.ResourceNotFoundException;
import com.microservices.user.UserService.payloads.APIResponse;
import com.microservices.user.UserService.repositories.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // MARK: - API's

    @Override
    public APIResponse saveUser(User user) {
        try {
            String randomUserId = UUID.randomUUID().toString();
            user.setId(randomUserId);
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
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserFromID(String userID) {
        User user = userRepository.findById(userID).orElseThrow(() -> 
            new ResourceNotFoundException("User with ID - " + userID + " not found..."));
        return user;
    }

    @Override
    public APIResponse deleteUser(String userID) {
        try {
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
            userRepository.save(user);
            return UserServiceImpl.getAPIResponse(
                UserControllerAPIResponseConstants.ADD_USER_SUCCESS,
                HttpStatus.CREATED,
                true);
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

}
