package com.microservices.user.UserService.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.microservices.user.UserService.entities.Rating;
import com.microservices.user.UserService.payloads.APIResponse;
import com.microservices.user.UserService.payloads.APIResponseWithRatings;

@FeignClient(name = "RATING-SERVICE", path = "/rating-service")
public interface RatingService {

    @PostMapping("/rating/new")
    APIResponse saveRating(@RequestBody Rating rating);

    @GetMapping("/ratings/user/{userID}")
    APIResponseWithRatings getRatingsGivenByUserWithID(@PathVariable("userID") String userID);

    @PutMapping("/rating/modify")
    APIResponse modifyRatingsGivenByUserWithID(@RequestBody Rating rating);

    @DeleteMapping("/rating/delete/{ratingID}")
    APIResponse deleteRating(@PathVariable String ratingID);

}
