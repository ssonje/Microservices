package com.microservices.hotel.repositories;

import com.microservices.hotel.entities.Hotel;
import com.microservices.hotel.helpers.HotelUnitTestHelper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class HotelRepositoryUnitTests {

    @Autowired
    private HotelRepository hotelRepository;

    // MARK: - Lifecycle methods

    @BeforeEach
    void setUp() {
        Hotel hotel = HotelUnitTestHelper.createHotelObjectWithHotelID();
        hotelRepository.save(hotel);
    }

    @AfterEach
    void tearDown() {
        hotelRepository.deleteAll();
    }

    // MARK: - Tests

    @Test
    void testGetHotelByID() {
        Hotel hotelFromRepository = HotelUnitTestHelper.getHotelFromOptionalHotelObject(hotelRepository);
        HotelUnitTestHelper.verifyHotelDetails(hotelFromRepository);
    }

    @Test
    void testGetAllHotels() {
        Hotel hotel = HotelUnitTestHelper.createHotelObjectWithHotelID();

        // Add hotel to the hotels list
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel);

        HotelUnitTestHelper.verifyGetHotelsLength(hotels, hotelRepository.findAll());
    }

    @Test
    void testSaveHotel() {
        Hotel hotelFromRepository = HotelUnitTestHelper.getHotelFromOptionalHotelObject(hotelRepository);
        HotelUnitTestHelper.verifyHotelDetails(hotelFromRepository);
    }

    @Test
    void testDeleteHotel() {
        Hotel hotel = HotelUnitTestHelper.createHotelObjectWithHotelID();
    
        // Add hotel to the hotels list
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel);

        // Before deleting information of one hotel
        HotelUnitTestHelper.verifyGetHotelsLength(hotels, hotelRepository.findAll());

        // Remove hotel from the hotels list and from the DB
        hotels.remove(0);
        hotelRepository.deleteById(HotelUnitTestHelper.TestHotelID);

        // After deleting information of one hotel
        HotelUnitTestHelper.verifyGetHotelsLength(hotels, hotelRepository.findAll());
    }

    @Test
    void testModifyHotel() {
        Hotel modifiedHotel = HotelUnitTestHelper.getHotelFromOptionalHotelObject(hotelRepository);
        String modifyName = HotelUnitTestHelper.TestHotelName + " Modified";
        modifiedHotel.setName(modifyName);
        hotelRepository.save(modifiedHotel);

        // Verify the hotel details
        Hotel hotelFromRepository = HotelUnitTestHelper.getHotelFromOptionalHotelObject(hotelRepository);
        HotelUnitTestHelper.verifyHotelDetails(hotelFromRepository, modifyName);
    }

}
