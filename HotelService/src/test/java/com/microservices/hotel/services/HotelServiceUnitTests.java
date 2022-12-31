package com.microservices.hotel.services;

import com.microservices.hotel.entities.Hotel;
import com.microservices.hotel.helpers.HotelUnitTestHelper;
import com.microservices.hotel.repositories.HotelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class HotelServiceUnitTests {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelRepository hotelRepository;

    // MARK: - Lifecycle methods

    @AfterEach
    void tearDown() {
        hotelRepository.deleteAll();
    }

    // MARK: - Tests

    @Test
    public void testGetHotelByID() {
        createAndSaveHotelWithID(hotelRepository);
        Hotel hotelFromService = hotelService.getHotelFromID(HotelUnitTestHelper.TestHotelID).getHotel();
        HotelUnitTestHelper.verifyHotelDetails(hotelFromService);
    }

    @Test
    public void testSaveHotelWithHotelID() {
        createAndSaveHotelWithID(hotelRepository);
        Hotel hotelFromService = hotelService.getHotelFromID(HotelUnitTestHelper.TestHotelID).getHotel();
        HotelUnitTestHelper.verifyHotelDetails(hotelFromService);
    }

    @Test
    public void testSaveHotelWithoutID() {
        createAndSaveHotelWithoutID(hotelRepository);
        List<Hotel> hotels = hotelRepository.findAll();
        HotelUnitTestHelper.verifyHotelWithoutIDDetails(hotels);
    }

    @Test
    public void testgetAllHotels() {
        createAndSaveHotelWithID(hotelRepository);

        Hotel hotel = HotelUnitTestHelper.createHotelObjectWithHotelID();
    
        // save two more hotel objects
        hotelService.saveHotel(hotel);
        hotelService.saveHotel(hotel);
    
        // Add three hotels to the hotels list
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel);
        hotels.add(hotel);
        hotels.add(hotel);

        HotelUnitTestHelper.verifyGetHotelsLength(hotels, hotelService.getAllHotels().getHotels());
    }

    @Test
    public void testdeleteHotel() {
        createAndSaveHotelWithID(hotelRepository);

        Hotel hotel = HotelUnitTestHelper.createHotelObjectWithHotelID();
    
        // save two more hotel objects
        hotelService.saveHotel(hotel);
        hotelService.saveHotel(hotel);
    
        // Add three hotels to the hotels list
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel);
        hotels.add(hotel);
        hotels.add(hotel);

        // Before deleting information of one hotel
        HotelUnitTestHelper.verifyGetHotelsLength(hotels, hotelService.getAllHotels().getHotels());

        // Remove hotel from the hotels list and from the DB
        hotels.remove(0);
        hotelService.deleteHotel(HotelUnitTestHelper.TestHotelID);

        // After deleting information of one hotel
        HotelUnitTestHelper.verifyGetHotelsLength(hotels, hotelService.getAllHotels().getHotels());
    }

    @Test
    public void testmodifyHotel() {
        createAndSaveHotelWithID(hotelRepository);

        Hotel modifiedHotel = HotelUnitTestHelper.getHotelFromOptionalHotelObject(hotelRepository);
        String modifiedName = HotelUnitTestHelper.TestHotelName + " Modified";
        modifiedHotel.setName(modifiedName);

        hotelService.modifyHotel(modifiedHotel);

        // Verify the hotel details
        Hotel hotelFromRepository = HotelUnitTestHelper.getHotelFromOptionalHotelObject(hotelRepository);
        HotelUnitTestHelper.verifyHotelDetails(hotelFromRepository, modifiedName);
    }

    // MARK: - Tests

    private static void createAndSaveHotelWithID(HotelRepository hotelRepository) {
        Hotel hotelWithID = HotelUnitTestHelper.createHotelObjectWithHotelID();
        hotelRepository.save(hotelWithID);
    }

    private static void createAndSaveHotelWithoutID(HotelRepository hotelRepository) {
        Hotel hotelWithoutID = HotelUnitTestHelper.createHotelObjectWithHotelID();
        hotelRepository.save(hotelWithoutID);
    }

}
