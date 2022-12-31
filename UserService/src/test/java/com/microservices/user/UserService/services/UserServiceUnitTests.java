package com.microservices.user.UserService.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.microservices.user.UserService.entities.User;
import com.microservices.user.UserService.helpers.UserUnitTestHelper;
import com.microservices.user.UserService.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    // MARK: - Lifecycle methods

    @BeforeEach
    void setUp() {
        User user = UserUnitTestHelper.createUserObjectWithUserID();
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    // MARK: - Tests

    @Test
    public void testGetUserByID() {
        createAndSaveUserObjectWithUserID(userRepository);
        User userFromService = userService.getUserFromID(UserUnitTestHelper.TestUserID).getUser();
        UserUnitTestHelper.verifyUserDetails(userFromService);
    }

    @Test
    public void testSaveUser() {
        createAndSaveUserObjectWithUserID(userRepository);
        User userFromService = userService.getUserFromID(UserUnitTestHelper.TestUserID).getUser();
        UserUnitTestHelper.verifyUserDetails(userFromService);
    }

    @Test
    public void testSaveUserWithoutID() {
        createAndSaveUserObjectWithoutUserID(userRepository);
        List<User> users = userRepository.findAll();
        UserUnitTestHelper.verifyUserWithoutIDDetails(users);
    }

    @Test
    public void testGetAllUsers() {
        createAndSaveUserObjectWithUserID(userRepository);
        User user = UserUnitTestHelper.createUserObjectWithUserID();

        // Add user to the users list
        List<User> users = new ArrayList<>();
        users.add(user);

        UserUnitTestHelper.verifyGetUsersLength(users, userService.getAllUsers().getUsers());
    }

    @Test
    public void testDeleteUser() {
        createAndSaveUserObjectWithUserID(userRepository);
        User user = UserUnitTestHelper.createUserObjectWithUserID();

        // Add user to the users list
        List<User> users = new ArrayList<>();
        users.add(user);

        // Before deleting information of one user
        UserUnitTestHelper.verifyGetUsersLength(users, userService.getAllUsers().getUsers());

        // Remove user from the users list and from the DB
        users.remove(0);
        userService.deleteUser(UserUnitTestHelper.TestUserID);

        // After deleting information of one user
        UserUnitTestHelper.verifyGetUsersLength(users, userService.getAllUsers().getUsers());
    }

    @Test
    public void testModifyUser() {
        createAndSaveUserObjectWithUserID(userRepository);
        User modifiedUser = UserUnitTestHelper.getUserFromOptionalUserObject(userRepository);
        String modifiedName = UserUnitTestHelper.TestUserName + " Modified";
        modifiedUser.setName(modifiedName);

        userService.modifyUser(modifiedUser);

        // Verify the user details
        User userFromRepository = UserUnitTestHelper.getUserFromOptionalUserObject(userRepository);
        UserUnitTestHelper.verifyUserDetails(userFromRepository, modifiedName);
    }

    // MARK: - Helper functions

    private static void createAndSaveUserObjectWithUserID(UserRepository userRepository) {
        User userWithID = UserUnitTestHelper.createUserObjectWithUserID();
        userRepository.save(userWithID);
    }

    private static void createAndSaveUserObjectWithoutUserID(UserRepository userRepository) {
        User userWithID = UserUnitTestHelper.createUserObjectWithoutUserID();
        userWithID.setId(UUID.randomUUID().toString());
        userRepository.save(userWithID);
    }

}
