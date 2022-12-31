package com.microservices.hotel.services;

import com.microservices.hotel.constants.HotelControllerAPIResponseConstants;
import com.microservices.hotel.entities.Hotel;
import com.microservices.hotel.exceptions.ResourceNotFoundException;
import com.microservices.hotel.payloads.APIResponse;
import com.microservices.hotel.payloads.APIResponseWithHotel;
import com.microservices.hotel.payloads.APIResponseWithHotels;
import com.microservices.hotel.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    // MARK: - API's

    @Override
    public APIResponse saveHotel(Hotel hotel) {
        try {
            // Set the Unique ID to the Hotel id
            String randomHotelId = UUID.randomUUID().toString();
            hotel.setId(randomHotelId);

            // Save the hotel object to the DB
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
    public APIResponseWithHotels getAllHotels() {
        try {
            List<Hotel> hotels = hotelRepository.findAll();
            return HotelServiceImpl.getAPIResponseForHotels(
                hotels,
                HotelControllerAPIResponseConstants.GET_ALL_HOTEL_SUCCESS,
                HttpStatus.OK,
                true);
        } catch (Exception e) {
            return HotelServiceImpl.getAPIResponseForHotels(new ArrayList<>(), e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    @Override
    public APIResponseWithHotel getHotelFromID(String hotelID) {
        try {
            Hotel hotel = hotelRepository.findById(hotelID).orElseThrow(() ->
                new ResourceNotFoundException("Hotel with ID - " + hotelID + " not found."));
            return HotelServiceImpl.getAPIResponseForHotel(
                hotel,
                HotelControllerAPIResponseConstants.GET_HOTEL_SUCCESS,
                HttpStatus.OK,
                true);
        } catch (Exception e) {
            return HotelServiceImpl.getAPIResponseForHotel(null, e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    @Override
    public APIResponse deleteHotel(String hotelID) {
        try {
            hotelRepository.findById(hotelID).orElseThrow(() ->
                new ResourceNotFoundException("Hotel with ID - " + hotelID + " not found."));
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
            String hotelID = hotel.getId();
            hotelRepository.findById(hotelID).orElseThrow(() ->
                new ResourceNotFoundException("Hotel with ID - " + hotelID + " not found."));
            hotelRepository.save(hotel);
            return HotelServiceImpl.getAPIResponse(
                HotelControllerAPIResponseConstants.MODIFIED_HOTEL_SUCCESS,
                HttpStatus.OK,
                true);
        } catch (Exception e) {
            return HotelServiceImpl.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    // MARK: - Helper API's

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

    @Override
    public APIResponse saveHotelWithID(Hotel hotel) {
        try {
            if (hotel.getId() != null) {
                hotelRepository.save(hotel);
                return HotelServiceImpl.getAPIResponse(
                    HotelControllerAPIResponseConstants.ADD_HOTEL_SUCCESS,
                    HttpStatus.CREATED,
                    true);
            } else {
                throw new ResourceNotFoundException("Hotel doesn't have hotelID.");
            }
        } catch (Exception e) {
            return HotelServiceImpl.getAPIResponse(e.getMessage(), HttpStatus.NOT_FOUND, false);
        }
    }

    // MARK: - Private helper methods

    private static APIResponse getAPIResponse(String message, HttpStatus httpStatus, Boolean responseStatus) {
        return APIResponse.builder()
            .message(message)
            .httpStatus(httpStatus)
            .responseStatus(responseStatus)
            .build();
    }

    private static APIResponseWithHotel getAPIResponseForHotel(Hotel hotel, String message, HttpStatus httpStatus, Boolean responseStatus) {
        APIResponse apiResponse = getAPIResponse(message, httpStatus, responseStatus);
        return APIResponseWithHotel.builder()
            .apiResponse(apiResponse)
            .hotel(hotel)
            .build();
    }

    private static APIResponseWithHotels getAPIResponseForHotels(List<Hotel> hotels, String message, HttpStatus httpStatus, Boolean responseStatus) {
        APIResponse apiResponse = getAPIResponse(message, httpStatus, responseStatus);
        return APIResponseWithHotels.builder()
            .apiResponse(apiResponse)
            .hotels(hotels)
            .build();
    }

}
