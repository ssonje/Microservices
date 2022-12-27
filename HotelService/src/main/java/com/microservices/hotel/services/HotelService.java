package com.microservices.hotel.services;

import com.microservices.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {

    Hotel saveHotel(Hotel hotel);
    List<Hotel> getAllHotels();
    Hotel getHotelFromID(Integer hotelID);
    Boolean deleteHotel(Integer hotelID);
    Hotel modifyHotel(Hotel hotel);
    Boolean deleteAllHotels();

}
