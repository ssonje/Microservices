package com.microservices.hotel.services;

import com.microservices.hotel.entities.Hotel;
import com.microservices.hotel.payloads.APIResponse;

import java.util.List;

public interface HotelService {

    APIResponse saveHotel(Hotel hotel);
    List<Hotel> getAllHotels();
    Hotel getHotelFromID(String hotelID);
    APIResponse deleteHotel(String hotelID);
    APIResponse  modifyHotel(Hotel hotel);
    APIResponse  deleteAllHotels();
    APIResponse saveHotelWithID(Hotel hotel);

}
