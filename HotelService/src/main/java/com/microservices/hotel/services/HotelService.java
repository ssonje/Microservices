package com.microservices.hotel.services;

import com.microservices.hotel.entities.Hotel;
import com.microservices.hotel.payloads.APIResponse;
import com.microservices.hotel.payloads.APIResponseWithHotel;
import com.microservices.hotel.payloads.APIResponseWithHotels;

public interface HotelService {

    APIResponse saveHotel(Hotel hotel);
    APIResponseWithHotels getAllHotels();
    APIResponseWithHotel getHotelFromID(String hotelID);
    APIResponse deleteHotel(String hotelID);
    APIResponse modifyHotel(Hotel hotel);
    APIResponse deleteAllHotels();
    APIResponse saveHotelWithID(Hotel hotel);

}
