package com.microservices.hotel.services;

import com.microservices.hotel.entities.Hotel;
import com.microservices.hotel.repositories.HotelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class HotelServiceUnitTests {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelRepository hotelRepository;

    private static String TestHotelID = UUID.randomUUID().toString();
    private static String TestHotelName = "Test Hotel Name";
    private static String TestHotelAbout = "Test Hotel About";
    private static String TestHotelLocation = "Test Hotel Location";

    @BeforeEach
    void setUp() {
        Hotel hotel = getHotelObject();
        hotelService.saveHotelWithID(hotel);
    }

    @AfterEach
    void tearDown() {
        hotelService.deleteAllHotels();
    }

    @Test
    public void testGetHotelByID() {
        Hotel hotelFromRepository = getHotelFromOptionalHotelObject(hotelRepository);
        verifyHotelDetails(hotelFromRepository, TestHotelAbout, TestHotelLocation, TestHotelName);
    }

    @Test
    public void testSaveHotel() {
        Hotel hotelFromRepository = getHotelFromOptionalHotelObject(hotelRepository);
        verifyHotelDetails(hotelFromRepository, TestHotelAbout, TestHotelLocation, TestHotelName);
    }

    @Test
    public void testgetAllHotels() {
        Hotel hotel = getHotelObject();
    
        // save two more hotel objects
        hotelService.saveHotel(hotel);
        hotelService.saveHotel(hotel);
    
        // Add three hotels to the hotels list
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel);
        hotels.add(hotel);
        hotels.add(hotel);

        verifyGetHotelsLength(hotels, hotelRepository.findAll());
    }

    @Test
    public void testdeleteHotel() {
        Hotel hotel = getHotelObject();
    
        // save two more hotel objects
        hotelService.saveHotel(hotel);
        hotelService.saveHotel(hotel);
    
        // Add three hotels to the hotels list
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel);
        hotels.add(hotel);
        hotels.add(hotel);

        // Before deleting information of one hotel
        verifyGetHotelsLength(hotels, hotelRepository.findAll());

        // Remove hotel from the hotels list and from the DB
        hotels.remove(0);
        hotelService.deleteHotel(TestHotelID);

        // After deleting information of one hotel
        verifyGetHotelsLength(hotels, hotelRepository.findAll());
    }

    @Test
    public void testmodifyHotel() {
        Hotel hotel = HotelServiceUnitTests.getHotelObject();
        hotelService.saveHotel(hotel);

        Hotel modifiedHotel = hotelRepository.findAll().get(0);
        modifiedHotel.setName(HotelServiceUnitTests.TestHotelName + " Modified");
        hotelService.modifyHotel(modifiedHotel);

        // Verify the hotel details
        Hotel hotelFromRepository = hotelRepository.findAll().get(0);
        HotelServiceUnitTests.verifyHotelDetails(hotelFromRepository, TestHotelAbout, TestHotelLocation, TestHotelName + " Modified");
    }

    private static Hotel getHotelObject() {
        Hotel hotel = Hotel.builder()
            .id(TestHotelID)
            .name(TestHotelName)
            .about(TestHotelAbout)
            .location(TestHotelLocation)
            .build();
        return hotel;
    }

    private static Hotel getHotelFromOptionalHotelObject(HotelRepository hotelRepository) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(TestHotelID);
        return hotelOptional.get();
    }

    private static void verifyHotelDetails(Hotel hotelFromRepository, String hotelAbout, String hotelLocation, String hotelName) {
        Assert.notNull(
            hotelFromRepository,
            "Hotel get from the service should be present."
        );

        Assertions.assertEquals(
            hotelAbout,
            hotelFromRepository.getAbout(),
            "Hotel About should be equal to the " + TestHotelAbout
        );

        Assertions.assertEquals(
            hotelLocation,
            hotelFromRepository.getLocation(),
            "Hotel Location should be equal to the " + TestHotelLocation
        );

        Assertions.assertEquals(
            hotelName,
            hotelFromRepository.getName(),
            "Hotel Name should be equal to the " + TestHotelName
        );
    }

    private static void verifyGetHotelsLength(List<Hotel> hotels, List<Hotel> hotelsGetFromRepository) {
        Integer hotelsLength = hotels.toArray().length;
        Integer hotelsGetFromRepositoryLegnth = hotelsGetFromRepository.toArray().length;
        Assertions.assertEquals(
            hotelsLength,
            hotelsGetFromRepositoryLegnth,
            "hotelListLength " + hotelsLength + " should match with the hotelRepositoryLength length = " + hotelsGetFromRepositoryLegnth
        );
    }

}
