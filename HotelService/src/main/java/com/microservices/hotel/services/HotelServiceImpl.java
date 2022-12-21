package com.microservices.hotel.services;

import com.microservices.hotel.entities.Hotel;
import com.microservices.hotel.exceptions.ResourceNotFoundException;
import com.microservices.hotel.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotelFromID(Integer hotelID) {
        Hotel hotel = hotelRepository.findById(hotelID).orElseThrow(() ->
                new ResourceNotFoundException("Hotel with ID - " + hotelID + " not found..."));
        return hotel;
    }

    @Override
    public Boolean deleteHotel(Integer hotelID) {
        hotelRepository.deleteById(hotelID);
        return true;
    }

    @Override
    public Hotel modifyHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

}
