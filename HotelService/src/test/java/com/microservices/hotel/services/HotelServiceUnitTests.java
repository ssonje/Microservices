package com.microservices.hotel.services;

import com.microservices.hotel.entities.Hotel;
import com.microservices.hotel.repositories.HotelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class HotelServiceUnitTests {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelRepository hotelRepository;

    private static String TestHotelName = "Test Hotel Name";
    private static String TestHotelAbout = "Test Hotel About";
    private static String TestHotelLocation = "Test Hotel Location";

    @AfterEach
    void tearDown() {
        hotelService.deleteAllHotels();
    }

    @Test
    public void testGetHotelByID() {
        Hotel hotel = HotelServiceUnitTests.getHotelObject();
        hotelService.saveHotel(hotel);

        // Verify the hotel details
        Hotel hotelFromRepository = hotelRepository.findAll().get(0);
        HotelServiceUnitTests.verifyHotelDetails(
                hotelFromRepository,
                HotelServiceUnitTests.TestHotelAbout,
                HotelServiceUnitTests.TestHotelLocation,
                HotelServiceUnitTests.TestHotelName
        );
    }

    @Test
    public void testSaveHotel() {
        Hotel hotel = HotelServiceUnitTests.getHotelObject();
        hotelService.saveHotel(hotel);

        // Verify the hotel details
        Hotel hotelFromRepository = hotelRepository.findAll().get(0);
        HotelServiceUnitTests.verifyHotelDetails(
                hotelFromRepository,
                HotelServiceUnitTests.TestHotelAbout,
                HotelServiceUnitTests.TestHotelLocation,
                HotelServiceUnitTests.TestHotelName
        );
    }

    @Test
    public void testgetAllHotels() {
        Hotel hotel = HotelServiceUnitTests.getHotelObject();
        List<Hotel> hotelList = new ArrayList<>();
        hotelList.add(hotel);

        hotelService.saveHotel(hotel);

        Assertions.assertEquals(
                hotelList.toArray().length,
                hotelRepository.findAll().toArray().length,
                "Hotel list should have length = 1"
        );
    }

    @Test
    public void testdeleteHotel() {
        Hotel hotel = HotelServiceUnitTests.getHotelObject();
        List<Hotel> hotelList = new ArrayList<>();
        hotelList.add(hotel);

        hotelService.saveHotel(hotel);

        // Before deleting information of one hotel
        Assertions.assertEquals(
                1,
                hotelRepository.findAll().toArray().length,
                "Hotel list should have length = 1"
        );

        hotelService.deleteHotel(hotelService.getAllHotels().get(0).getId());

        // After deleting information of one hotel
        Assertions.assertEquals(
                0,
                hotelRepository.findAll().toArray().length,
                "Hotel list should have length = 0"
        );
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
        HotelServiceUnitTests.verifyHotelDetails(
                hotelFromRepository,
                HotelServiceUnitTests.TestHotelAbout,
                HotelServiceUnitTests.TestHotelLocation,
                HotelServiceUnitTests.TestHotelName + " Modified"
        );
    }

    private static Hotel getHotelObject() {
        Hotel hotel = new Hotel().builder()
                .name(HotelServiceUnitTests.TestHotelName)
                .about(HotelServiceUnitTests.TestHotelAbout)
                .location(HotelServiceUnitTests.TestHotelLocation)
                .build();
        return hotel;
    }

    private static void verifyHotelDetails(Hotel hotelFromRepository,
                                            String hotelAbout,
                                            String hotelLocation,
                                            String hotelName) {
        Assert.notNull(
                hotelFromRepository,
                "Hotel get from the service should be present."
        );

        Assertions.assertEquals(
                hotelAbout,
                hotelFromRepository.getAbout(),
                "Hotel About should be equal to the " + HotelServiceUnitTests.TestHotelAbout
        );

        Assertions.assertEquals(
                hotelLocation,
                hotelFromRepository.getLocation(),
                "Hotel Location should be equal to the " + HotelServiceUnitTests.TestHotelLocation
        );

        Assertions.assertEquals(
                hotelName,
                hotelFromRepository.getName(),
                "Hotel Name should be equal to the " + HotelServiceUnitTests.TestHotelName
        );
    }

}
