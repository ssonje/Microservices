package com.microservices.hotel.payloads;

import lombok.*;

import com.microservices.hotel.entities.Hotel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponseWithHotel {

    private Hotel hotel;
    private APIResponse apiResponse;

}
