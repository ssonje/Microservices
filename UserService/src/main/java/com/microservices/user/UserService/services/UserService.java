package com.microservices.user.UserService.services;

import com.microservices.user.UserService.entities.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);
    List<User> getAllUsers();
    User getUserFromID(String userID);
    Boolean deleteUser(String userID);
    User modifyUser(User user);

}
