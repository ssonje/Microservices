package com.microservices.user.UserService.payloads;

import com.microservices.user.UserService.entities.Hotel;

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
public class APIResponseWithHotel {

    private Hotel hotel;
    private APIResponse apiResponse;

}
