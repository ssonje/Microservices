package com.microservices.user.UserService.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.microservices.user.UserService.entities.User;
import com.microservices.user.UserService.helpers.UserUnitTestHelper;
import com.microservices.user.UserService.helpers.constants.UserControllerTestAPIResponseConstants;
import com.microservices.user.UserService.payloads.APIResponse;
import com.microservices.user.UserService.services.UserService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserControllerUnitTest extends AbstractTest {

    @Autowired
    private UserService userService;

    // MARK: - Lifecycle Methods

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        User user = UserUnitTestHelper.createUserObject();
        userService.saveUserByUserID(user);
    }

    @AfterEach
    public void tearDown() {
        userService.deleteAllUsers();
    }

    // MARK: - Tests

    @Test
    public void testGetUserWithID() throws Exception {
        String getUserWithIDURLString = "/user-service/user/{userID}";

        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders
                .get(getUserWithIDURLString, UserUnitTestHelper.TestUserID)
                .accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        String userFromURLResponse = mvcResult.getResponse().getContentAsString();
        User userResponse = super.mapFromJson(userFromURLResponse, User.class);
        UserUnitTestHelper.verifyUserDetails(userResponse);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        String getUsersURLString = "/user-service/users";

        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.get(getUsersURLString).accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        User[] usersList = super.mapFromJson(content, User[].class);
        Assertions.assertTrue(usersList.length > 0);
    }

    @Test
    public void testSaveUser() throws Exception {
        String saveUserURLString = "/user-service/user/new";

        User user = UserUnitTestHelper.createUserObject();
        String inputJson = super.mapToJson(user);

        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.post(saveUserURLString).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
            .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        APIResponse apiResponse = super.mapFromJson(response, APIResponse.class);

        // Verify API response details
        UserUnitTestHelper.verifyAPIResponse(apiResponse, true, HttpStatus.CREATED, UserControllerTestAPIResponseConstants.ADD_USER_SUCCESS);
    }

    @Test
    public void testModifyUser() throws Exception {
        String modifyUserURLString = "/user-service/user/modify";

        User modifiedUser = userService.getUserFromID(UserUnitTestHelper.TestUserID);
        modifiedUser.setName(UserUnitTestHelper.TestUserName + " Modified");

        String inputJson = super.mapToJson(modifiedUser);
        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.put(modifyUserURLString).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
            .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        APIResponse apiResponse = super.mapFromJson(response, APIResponse.class);

        // Verify API response details
        UserUnitTestHelper.verifyAPIResponse(apiResponse, true, HttpStatus.OK, UserControllerTestAPIResponseConstants.MODIFIED_USER_SUCCESS);
    }

    @Test
    public void testDeleteUser() throws Exception {
        String deleteUserURLString = "/user-service/user/delete/{userID}";

        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.delete(deleteUserURLString, UserUnitTestHelper.TestUserID).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        APIResponse apiResponse = super.mapFromJson(response, APIResponse.class);

        // Verify API response details
        UserUnitTestHelper.verifyAPIResponse(apiResponse, true, HttpStatus.OK, UserControllerTestAPIResponseConstants.DELETE_USER_SUCCESS);
    }

}
