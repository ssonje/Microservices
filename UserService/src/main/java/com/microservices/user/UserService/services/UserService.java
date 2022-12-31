package com.microservices.user.UserService.services;

import com.microservices.user.UserService.entities.User;
import com.microservices.user.UserService.payloads.APIResponse;
import com.microservices.user.UserService.payloads.APIResponseWithUser;
import com.microservices.user.UserService.payloads.APIResponseWithUsers;

public interface UserService {

    APIResponse saveUser(User user);
    APIResponseWithUsers getAllUsers();
    APIResponseWithUser getUserFromID(String userID);
    APIResponse deleteUser(String userID);
    APIResponse modifyUser(User user);
    APIResponse deleteAllUsers();
    APIResponse saveUserByUserID(User user);

}
