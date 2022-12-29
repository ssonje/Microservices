package com.microservices.user.UserService.external.services;

import com.microservices.user.UserService.entities.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTEL-SERVICE", path = "/hotel-service")
public interface HotelService {

    @GetMapping("/hotel/{hotelID}")
    Hotel getHotel(@PathVariable("hotelID") String hotelID);

}
