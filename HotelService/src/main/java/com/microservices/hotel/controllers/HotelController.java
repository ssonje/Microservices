package com.microservices.hotel.controllers;

import com.microservices.hotel.entities.Hotel;
import com.microservices.hotel.payloads.APIResponse;
import com.microservices.hotel.payloads.APIResponseWithHotel;
import com.microservices.hotel.payloads.APIResponseWithHotels;
import com.microservices.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotel-service")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/hotel/new")
    public ResponseEntity<?> saveHotel(@RequestBody Hotel hotel) {
        APIResponse apiResponse;
        if (hotel.getId() != null) {
            apiResponse = hotelService.saveHotelWithID(hotel);
        } else {
            apiResponse = hotelService.saveHotel(hotel);
        }

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/hotel/{hotelID}")
    public ResponseEntity<?> getHotelFromID(@PathVariable String hotelID) {
        APIResponseWithHotel apiResponse = hotelService.getHotelFromID(hotelID);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/hotels")
    public ResponseEntity<?> getAllHotels() {
        APIResponseWithHotels apiResponse = hotelService.getAllHotels();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/hotel/delete/{hotelID}")
    public ResponseEntity<?> deleteHotel(@PathVariable String hotelID) {
        APIResponse apiResponse = hotelService.deleteHotel(hotelID);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/hotel/modify")
    public ResponseEntity<?> modifyHotel(@RequestBody Hotel hotel) {
        APIResponse apiResponse = hotelService.modifyHotel(hotel);
        return ResponseEntity.ok(apiResponse);
    }

}
