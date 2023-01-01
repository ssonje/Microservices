package com.microservices.user.UserService.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.microservices.user.UserService.entities.Hotel;
import com.microservices.user.UserService.payloads.APIResponse;
import com.microservices.user.UserService.payloads.APIResponseWithHotel;

@FeignClient(name = "HOTEL-SERVICE", path = "/hotel-service")
public interface HotelService {

    @PostMapping("/hotel/new")
    APIResponse saveHotel(@RequestBody Hotel hotel);

    @GetMapping("/hotel/{hotelID}")
    APIResponseWithHotel getHotelFromID(@PathVariable("hotelID") String hotelID);

    @PutMapping("/hotel/modify")
    APIResponse modifyHotel(@RequestBody Hotel hotel);

    @DeleteMapping("/hotel/delete/{hotelID}")
    APIResponse deleteHotel(@PathVariable String hotelID);

}
