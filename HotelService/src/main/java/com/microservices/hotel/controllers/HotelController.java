package com.microservices.hotel.controllers;

import com.microservices.hotel.entities.Hotel;
import com.microservices.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/hotel/new")
    public ResponseEntity<?> createHotel(@RequestBody Hotel hotel) {
        Hotel savedHotel = hotelService.saveHotel(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHotel);
    }

    @GetMapping("/hotel/{hotelID}")
    public ResponseEntity<?> getHotelFromID(@PathVariable Integer hotelID) {
        Hotel hotel = hotelService.getHotelFromID(hotelID);
        return ResponseEntity.status(HttpStatus.OK).body(hotel);
    }

    @GetMapping("/hotels")
    public ResponseEntity<?> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        return ResponseEntity.status(HttpStatus.OK).body(hotels);
    }

    @PostMapping("/hotel/delete/{hotelID}")
    public ResponseEntity<?> deleteHotel(@PathVariable Integer hotelID) {
        Boolean isUserDeleted = hotelService.deleteHotel(hotelID);
        return ResponseEntity.status(HttpStatus.CREATED).body(isUserDeleted);
    }

    @PostMapping("/hotel/modify")
    public ResponseEntity<?> modifyHotel(@RequestBody Hotel hotel) {
        Hotel modifiedHotel = hotelService.modifyHotel(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(modifiedHotel);
    }

}
