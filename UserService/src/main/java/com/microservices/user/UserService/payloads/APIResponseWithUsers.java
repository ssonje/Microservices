package com.microservices.user.UserService.payloads;

import java.util.List;

import com.microservices.user.UserService.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponseWithUsers {

    private List<User> users;
    private APIResponse apiResponse;

}