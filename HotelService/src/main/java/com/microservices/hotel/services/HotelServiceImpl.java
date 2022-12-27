package com.microservices.hotel.services;

import com.microservices.hotel.constants.HotelControllerAPIResponseConstants;
import com.microservices.hotel.entities.Hotel;
import com.microservices.hotel.exceptions.ResourceNotFoundException;
import com.microservices.hotel.payloads.APIResponse;
import com.microservices.hotel.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public APIResponse saveHotel(Hotel hotel) {
        try {
            hotelRepository.save(hotel);
            return HotelServiceImpl.getAPIResponse(
                    HotelControllerAPIResponseConstants.ADD_HOTEL_SUCCESS,
                    HttpStatus.CREATED,
                    true);
        } catch (Exception e) {
            return HotelServiceImpl.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
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
    public APIResponse deleteHotel(Integer hotelID) {
        try {
            hotelRepository.deleteById(hotelID);
            return HotelServiceImpl.getAPIResponse(
                    HotelControllerAPIResponseConstants.DELETE_HOTEL_SUCCESS,
                    HttpStatus.OK,
                    true);
        } catch (Exception e) {
            return HotelServiceImpl.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    @Override
    public APIResponse modifyHotel(Hotel hotel) {
        try {
            Optional<Hotel> hotelOptional = hotelRepository.findById(hotel.getId());
            Hotel hotelFromID = hotelOptional.get();
            hotelFromID.setName(hotel.getName());
            hotelFromID.setAbout(hotel.getAbout());
            hotelFromID.setLocation(hotel.getLocation());
            hotelRepository.save(hotelFromID);
            return HotelServiceImpl.getAPIResponse(
                    HotelControllerAPIResponseConstants.MODIFIED_HOTEL_SUCCESS,
                    HttpStatus.OK,
                    true);
        } catch (Exception e) {
            return HotelServiceImpl.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    @Override
    public APIResponse deleteAllHotels() {
        try {
            hotelRepository.deleteAll();
            return HotelServiceImpl.getAPIResponse(
                    HotelControllerAPIResponseConstants.DELETE_ALL_HOTELS_SUCCESS,
                    HttpStatus.OK,
                    true);
        } catch (Exception e) {
            return HotelServiceImpl.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    private static APIResponse getAPIResponse(String message, HttpStatus httpStatus, Boolean responseStatus) {
        return APIResponse.builder()
                .message(message)
                .httpStatus(httpStatus)
                .responseStatus(responseStatus)
                .build();
    }
}
