package com.microservices.user.UserService.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    private Integer id;

    private Integer userID;
    private Integer hotelID;
    private Integer rating;
    private String feedback;

    private Hotel hotel;

}
