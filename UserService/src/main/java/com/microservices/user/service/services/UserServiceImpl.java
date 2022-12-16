package com.microservices.user.service.services;

import com.microservices.user.service.entities.User;
import com.microservices.user.service.exceptions.ResourceNotFoundException;
import com.microservices.user.service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserFromID(String userID) {
        User user = userRepository.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User with ID - " + userID + " not found..."));
        return user;
    }

    @Override
    public Boolean deleteUser(String userID) {
        userRepository.deleteById(userID);
        return true;
    }

    @Override
    public User modifyUser(User user) {
        return userRepository.save(user);
    }

}
