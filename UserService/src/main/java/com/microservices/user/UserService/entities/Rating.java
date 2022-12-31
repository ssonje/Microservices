package com.microservices.user.UserService.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {

    private String id;
    private String userID;
    private String hotelID;
    private Integer rating;
    private String feedback;
    private Hotel hotel;

}
