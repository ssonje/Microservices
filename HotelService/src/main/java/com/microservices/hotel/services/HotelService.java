package com.microservices.hotel.services;

import com.microservices.hotel.entities.Hotel;
import com.microservices.hotel.payloads.APIResponse;

import java.util.List;

public interface HotelService {

    APIResponse saveHotel(Hotel hotel);
    List<Hotel> getAllHotels();
    Hotel getHotelFromID(Integer hotelID);
    APIResponse deleteHotel(Integer hotelID);
    APIResponse  modifyHotel(Hotel hotel);
    APIResponse  deleteAllHotels();

}
