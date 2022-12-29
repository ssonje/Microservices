package com.microservices.hotel.repositories;

import com.microservices.hotel.entities.Hotel;
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
public class HotelRepositoryUnitTests {

    @Autowired
    private HotelRepository hotelRepository;

    private static String TestHotelID = UUID.randomUUID().toString();
    private static String TestHotelName = "Test Hotel Name";
    private static String TestHotelAbout = "Test Hotel About";
    private static String TestHotelLocation = "Test Hotel Location";

    // MARK: - Lifecycle methods

    @BeforeEach
    void setUp() {
        Hotel hotel = getHotelObject();
        hotelRepository.save(hotel);
    }

    @AfterEach
    void tearDown() {
        hotelRepository.deleteAll();
    }

    // MARK: - Tests

    @Test
    void testGetHotelByID() {
        Hotel hotelFromRepository = getHotelFromOptionalHotelObject(hotelRepository);
        verifyHotelDetails(hotelFromRepository, TestHotelAbout, TestHotelLocation, TestHotelName);
    }

    @Test
    void testGetAllHotels() {
        Hotel hotel = getHotelObject();

        // Add three hotels to the hotels list
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel);

        verifyGetHotelsLength(hotels, hotelRepository.findAll());
    }

    @Test
    void testSaveHotel() {
        Hotel hotelFromRepository = getHotelFromOptionalHotelObject(hotelRepository);
        verifyHotelDetails(hotelFromRepository, TestHotelAbout, TestHotelLocation, TestHotelName);
    }

    @Test
    void testDeleteHotel() {
        Hotel hotel = getHotelObject();
    
        // Add three hotels to the hotels list
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel);

        // Before deleting information of one hotel
        verifyGetHotelsLength(hotels, hotelRepository.findAll());

        // Remove hotel from the hotels list and from the DB
        hotels.remove(0);
        hotelRepository.deleteById(TestHotelID);

        // After deleting information of one hotel
        verifyGetHotelsLength(hotels, hotelRepository.findAll());
    }

    @Test
    void testModifyHotel() {
        Hotel hotel = getHotelObject();
        hotelRepository.save(hotel);

        Hotel modifiedHotel = getHotelFromOptionalHotelObject(hotelRepository);
        modifiedHotel.setName(TestHotelName + " Modified");
        hotelRepository.save(modifiedHotel);

        // Verify the hotel details
        Hotel hotelFromRepository = getHotelFromOptionalHotelObject(hotelRepository);
        verifyHotelDetails(hotelFromRepository, TestHotelAbout, TestHotelLocation, TestHotelName + " Modified");
    }

    // MARK: - Private Helpers

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
