package com.microservices.hotel.helpers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import com.microservices.hotel.entities.Hotel;
import com.microservices.hotel.payloads.APIResponse;
import com.microservices.hotel.repositories.HotelRepository;

public final class HotelUnitTestHelper {

    public static String TestHotelID = UUID.randomUUID().toString();
    public static String TestHotelName = "Test Hotel Name";
    public static String TestHotelAbout = "Test Hotel About";
    public static String TestHotelLocation = "Test Hotel Location";

    public static Hotel createHotelObjectWithHotelID() {
        Hotel hotel = Hotel.builder()
            .id(TestHotelID)
            .name(TestHotelName)
            .about(TestHotelAbout)
            .location(TestHotelLocation)
            .build();
        return hotel;
    }

    public static Hotel createHotelObjectWithoutHotelID() {
        Hotel hotel = Hotel.builder()
            .name(TestHotelName)
            .about(TestHotelAbout)
            .location(TestHotelLocation)
            .build();
        return hotel;
    }

    public static Hotel getHotelFromOptionalHotelObject(HotelRepository hotelRepository) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(TestHotelID);
        return hotelOptional.get();
    }

    public static void verifyHotelDetails(Hotel hotel, String hotelAbout, String hotelLocation, String hotelName) {
        Assert.notNull(hotel, "Hotel get from the response should not be nil.");
        Assertions.assertEquals(hotelAbout, hotel.getAbout());
        Assertions.assertEquals(hotelLocation, hotel.getLocation());
        Assertions.assertEquals(hotelName, hotel.getName());
    }

    public static void verifyHotelDetails(Hotel hotel) {
        verifyHotelDetails(hotel, TestHotelAbout, TestHotelLocation, TestHotelName);
    }

    public static void verifyHotelDetails(Hotel hotel, String hotelName) {
        verifyHotelDetails(hotel, TestHotelAbout, TestHotelLocation, hotelName);
    }

    public static void verifyHotelWithoutIDDetails(List<Hotel> hotels) {
        Hotel requiredHotel = hotels.get(0);
        Assertions.assertEquals(hotels.toArray().length > 0, true);
        Assertions.assertNotNull(requiredHotel.getId());
        Assertions.assertNotNull(requiredHotel.getAbout());
        Assertions.assertNotNull(requiredHotel.getLocation());
        Assertions.assertNotNull(requiredHotel.getName());
    }

    public static void verifyAPIResponse(APIResponse apiResponse,
        Boolean expectedResponseStatus,
        HttpStatus expectedHttpStatus,
        String expectedMessage) {
        Assertions.assertEquals(expectedResponseStatus, apiResponse.getResponseStatus());
        Assertions.assertEquals(expectedHttpStatus, apiResponse.getHttpStatus());
        Assertions.assertEquals(expectedMessage, apiResponse.getMessage());
    }

    public static void verifyGetHotelsLength(List<Hotel> hotels, List<Hotel> hotelsGetFromRepository) {
        Integer hotelsLength = hotels.toArray().length;
        Integer hotelsGetFromRepositoryLegnth = hotelsGetFromRepository.toArray().length;
        Assertions.assertEquals(hotelsLength, hotelsGetFromRepositoryLegnth);
    }

}
