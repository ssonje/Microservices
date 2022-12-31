package com.microservices.user.UserService.payloads;

import java.util.List;

import com.microservices.user.UserService.entities.Rating;

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
public class APIResponseWithRatings {

    private List<Rating> ratings;
    private APIResponse apiResponse;

}