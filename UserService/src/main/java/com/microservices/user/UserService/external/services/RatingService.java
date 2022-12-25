package com.microservices.user.UserService.external.services;

import com.microservices.user.UserService.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "RATING-SERVICE", path = "/rating-service")
public interface RatingService {

    @GetMapping("/ratings/user/{userID}")
    List<Rating> getRatingsGivenByUserWithID(@PathVariable("userID") Integer userID);

}
