package com.microservices.rating.RatingService.payloads;

import lombok.*;

import java.util.List;

import com.microservices.rating.RatingService.entities.Rating;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponseWithRatings {

    private List<Rating> ratings;
    private APIResponse apiResponse;

}