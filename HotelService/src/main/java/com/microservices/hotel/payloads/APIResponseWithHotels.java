package com.microservices.hotel.payloads;

import lombok.*;

import java.util.List;

import com.microservices.hotel.entities.Hotel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponseWithHotels {

    private List<Hotel> hotels;
    private APIResponse apiResponse;

}
