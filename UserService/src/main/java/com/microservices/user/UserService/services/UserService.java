package com.microservices.user.UserService.services;

import com.microservices.user.UserService.entities.User;
import com.microservices.user.UserService.payloads.APIResponse;

import java.util.List;

public interface UserService {

    APIResponse saveUser(User user);
    List<User> getAllUsers();
    User getUserFromID(String userID);
    APIResponse deleteUser(String userID);
    APIResponse modifyUser(User user);
    APIResponse deleteAllUsers();
    APIResponse saveUserByUserID(User user);

}
