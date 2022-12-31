package com.microservices.user.UserService.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.microservices.user.UserService.entities.User;
import com.microservices.user.UserService.helpers.UserUnitTestHelper;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserRepositoryUnitTests {

    @Autowired
    private UserRepository userRepository;

    // MARK: - Lifecycle methods

    @BeforeEach
    void setUp() {
        User user = UserUnitTestHelper.createUserObject();
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    // MARK: - Tests

    @Test
    void testGetUserByID() {
        User userFromRepository = UserUnitTestHelper.getUserFromOptionalUserObject(userRepository);
        UserUnitTestHelper.verifyUserDetails(userFromRepository);
    }

    @Test
    void testGetAllUsers() {
        User user = UserUnitTestHelper.createUserObject();

        // Add user to the users list
        List<User> users = new ArrayList<>();
        users.add(user);

        UserUnitTestHelper.verifyGetUsersLength(users, userRepository.findAll());
    }

    @Test
    void testSaveUser() {
        User userFromRepository = UserUnitTestHelper.getUserFromOptionalUserObject(userRepository);
        UserUnitTestHelper.verifyUserDetails(userFromRepository);
    }

    @Test
    void testDeleteUserByID() {
        User user = UserUnitTestHelper.createUserObject();
    
        // Add user to the users list
        List<User> users = new ArrayList<>();
        users.add(user);

        // Before deleting information of one user
        UserUnitTestHelper.verifyGetUsersLength(users, userRepository.findAll());

        // Remove user from the users list and from the DB
        users.remove(0);
        userRepository.deleteById(UserUnitTestHelper.TestUserID);

        // After deleting information of one user
        UserUnitTestHelper.verifyGetUsersLength(users, userRepository.findAll());
    }

    @Test
    void testModifyUser() {
        User modifiedUser = UserUnitTestHelper.getUserFromOptionalUserObject(userRepository);
        String modifyName = UserUnitTestHelper.TestUserName + " Modified";
        modifiedUser.setName(modifyName);
        userRepository.save(modifiedUser);

        // Verify the user details
        User userFromRepository = UserUnitTestHelper.getUserFromOptionalUserObject(userRepository);
        UserUnitTestHelper.verifyUserDetails(userFromRepository, modifyName);
    }

}
