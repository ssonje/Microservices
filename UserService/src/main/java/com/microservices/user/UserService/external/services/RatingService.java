package com.microservices.user.UserService.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservices.user.UserService.payloads.APIResponseWithRatings;

@FeignClient(name = "RATING-SERVICE", path = "/rating-service")
public interface RatingService {

    @GetMapping("/ratings/user/{userID}")
    APIResponseWithRatings getRatingsGivenByUserWithID(@PathVariable("userID") String userID);

}
