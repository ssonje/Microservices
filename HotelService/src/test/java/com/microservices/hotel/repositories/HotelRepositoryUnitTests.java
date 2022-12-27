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

import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class HotelRepositoryUnitTests {

    @Autowired
    private HotelRepository hotelRepository;

    private static String TestHotelName = "Test Hotel Name";
    private static String TestHotelAbout = "Test Hotel About";
    private static String TestHotelLocation = "Test Hotel Location";

    @BeforeEach
    void setUp() {
        Hotel hotel = new Hotel().builder()
                .name(HotelRepositoryUnitTests.TestHotelName)
                .about(HotelRepositoryUnitTests.TestHotelAbout)
                .location(HotelRepositoryUnitTests.TestHotelLocation)
                .build();
        hotelRepository.save(hotel);
    }

    @AfterEach
    void tearDown() {
        hotelRepository.deleteAll();
    }

    @Test
    void testGetHotelByID() {
        List<Hotel> hotelList = hotelRepository.findAll();
        Assertions.assertEquals(
                1,
                hotelList.toArray().length,
                "Length of Hotels array should be equal to One."
        );

        Hotel hotel = hotelList.get(0);
        Assert.notNull(
                hotel,
                "Hotel which is setup in the setUp method should be present."
        );

        Assertions.assertEquals(
                HotelRepositoryUnitTests.TestHotelAbout,
                hotel.getAbout(),
                "Hotel About should be equal to the " + HotelRepositoryUnitTests.TestHotelAbout
        );

        Assertions.assertEquals(
                HotelRepositoryUnitTests.TestHotelLocation,
                hotel.getLocation(),
                "Hotel Location should be equal to the " + HotelRepositoryUnitTests.TestHotelLocation
        );

        Assertions.assertEquals(
                HotelRepositoryUnitTests.TestHotelName,
                hotel.getName(),
                "Hotel Name should be equal to the " + HotelRepositoryUnitTests.TestHotelName
        );
    }

    @Test
    void testGetAllHotels() {
        List<Hotel> hotelList = hotelRepository.findAll();
        Assertions.assertEquals(
                1,
                hotelList.toArray().length,
                "Length of Hotels array should be equal to One."
        );
    }

    @Test
    void testSaveHotel() {
        List<Hotel> hotelList = hotelRepository.findAll();
        Assertions.assertEquals(
                1,
                hotelList.toArray().length,
                "Length of Hotels array should be equal to One."
        );
    }

    @Test
    void testDeleteHotel() {
        hotelRepository.deleteAll();
        List<Hotel> hotelList = hotelRepository.findAll();
        Assertions.assertEquals(
                0,
                hotelList.toArray().length,
                "Length of Hotels array should be equal to Zero."
        );
    }

    @Test
    void testModifyHotel() {
        List<Hotel> hotelList = hotelRepository.findAll();
        Hotel hotel = hotelList.get(0);
        Assertions.assertEquals(
                1,
                hotelList.toArray().length,
                "Length of Hotels array should be equal to One."
        );

        hotel.setName("Modified Test Name");
        hotelRepository.save(hotel);

        List<Hotel> hotelListModified = hotelRepository.findAll();
        Hotel hotelModified = hotelListModified.get(0);
        Assertions.assertEquals(
                1,
                hotelListModified.toArray().length,
                "Length of Hotels array should be equal to One."
        );

        Assertions.assertEquals(
                "Modified Test Name",
                hotelModified.getName(),
                "Modify Hotel operation is not working as expected."
        );
    }

}
