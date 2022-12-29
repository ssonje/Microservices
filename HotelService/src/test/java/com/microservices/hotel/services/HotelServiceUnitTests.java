package com.microservices.hotel.services;

import com.microservices.hotel.entities.Hotel;
import com.microservices.hotel.helpers.HotelUnitTestHelper;
import com.microservices.hotel.repositories.HotelRepository;
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
public class HotelServiceUnitTests {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelRepository hotelRepository;

    // MARK: - Lifecycle methods

    @BeforeEach
    void setUp() {
        Hotel hotel = HotelUnitTestHelper.createHotelObject();
        hotelRepository.save(hotel);
    }

    @AfterEach
    void tearDown() {
        hotelRepository.deleteAll();
    }

    // MARK: - Tests

    @Test
    public void testGetHotelByID() {
        Hotel hotelFromService = hotelService.getHotelFromID(HotelUnitTestHelper.TestHotelID);
        HotelUnitTestHelper.verifyHotelDetails(hotelFromService);
    }

    @Test
    public void testSaveHotel() {
        Hotel hotelFromService = hotelService.getHotelFromID(HotelUnitTestHelper.TestHotelID);
        HotelUnitTestHelper.verifyHotelDetails(hotelFromService);
    }

    @Test
    public void testgetAllHotels() {
        Hotel hotel = HotelUnitTestHelper.createHotelObject();
    
        // save two more hotel objects
        hotelService.saveHotel(hotel);
        hotelService.saveHotel(hotel);
    
        // Add three hotels to the hotels list
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel);
        hotels.add(hotel);
        hotels.add(hotel);

        HotelUnitTestHelper.verifyGetHotelsLength(hotels, hotelService.getAllHotels());
    }

    @Test
    public void testdeleteHotel() {
        Hotel hotel = HotelUnitTestHelper.createHotelObject();
    
        // save two more hotel objects
        hotelService.saveHotel(hotel);
        hotelService.saveHotel(hotel);
    
        // Add three hotels to the hotels list
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel);
        hotels.add(hotel);
        hotels.add(hotel);

        // Before deleting information of one hotel
        HotelUnitTestHelper.verifyGetHotelsLength(hotels, hotelService.getAllHotels());

        // Remove hotel from the hotels list and from the DB
        hotels.remove(0);
        hotelService.deleteHotel(HotelUnitTestHelper.TestHotelID);

        // After deleting information of one hotel
        HotelUnitTestHelper.verifyGetHotelsLength(hotels, hotelService.getAllHotels());
    }

    @Test
    public void testmodifyHotel() {
        Hotel modifiedHotel = HotelUnitTestHelper.getHotelFromOptionalHotelObject(hotelRepository);
        String modifiedName = HotelUnitTestHelper.TestHotelName + " Modified";
        modifiedHotel.setName(modifiedName);

        hotelService.modifyHotel(modifiedHotel);

        // Verify the hotel details
        Hotel hotelFromRepository = HotelUnitTestHelper.getHotelFromOptionalHotelObject(hotelRepository);
        HotelUnitTestHelper.verifyHotelDetails(hotelFromRepository, modifiedName);
    }

}
