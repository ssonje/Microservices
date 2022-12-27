package com.microservices.hotel.controllers;

import com.microservices.hotel.entities.Hotel;
import com.microservices.hotel.services.HotelService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class HotelControllerUnitTest extends AbstractTest {

    private static String TestHotelName = "Test Hotel Name";
    private static String TestHotelAbout = "Test Hotel About";
    private static String TestHotelLocation = "Test Hotel Location";

    @Autowired
    private HotelService hotelService;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @AfterEach
    public void tearDown() {
        hotelService.deleteAllHotels();
    }

    @Test
    public void testGetHotelWithID() throws Exception {
        String uri = "/hotel-service/hotel/{hotelID}";

        Hotel hotel = HotelControllerUnitTest.getHotelObject();
        hotelService.saveHotel(hotel);

        Integer hotelID = hotelService.getAllHotels().get(0).getId();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get(uri, hotelID)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        String hotelFromURLResponse = mvcResult.getResponse().getContentAsString();
        Hotel hotelResponse = super.mapFromJson(hotelFromURLResponse, Hotel.class);
        verifyHotelDetails(
                hotelResponse,
                HotelControllerUnitTest.TestHotelAbout,
                HotelControllerUnitTest.TestHotelLocation,
                HotelControllerUnitTest.TestHotelName
        );
    }

    @Test
    public void testGetAllHotels() throws Exception {
        String uri = "/hotel-service/hotels";

        Hotel hotel = HotelControllerUnitTest.getHotelObject();
        hotelService.saveHotel(hotel);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        Hotel[] hotelsList = super.mapFromJson(content, Hotel[].class);
        Assertions.assertTrue(hotelsList.length > 0);
    }

    @Test
    public void testCreateHotel() throws Exception {
        String uri = "/hotel-service/hotel/new";

        Hotel hotel = HotelControllerUnitTest.getHotelObject();

        String inputJson = super.mapToJson(hotel);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(201, status);

        String hotelFromURLResponse = mvcResult.getResponse().getContentAsString();
        Hotel hotelResponse = super.mapFromJson(hotelFromURLResponse, Hotel.class);
        verifyHotelDetails(
                hotelResponse,
                HotelControllerUnitTest.TestHotelAbout,
                HotelControllerUnitTest.TestHotelLocation,
                HotelControllerUnitTest.TestHotelName
        );
    }

    @Test
    public void testModifyHotel() throws Exception {
        String uri = "/hotel-service/hotel/modify";

        Hotel hotel = HotelControllerUnitTest.getHotelObject();
        hotelService.saveHotel(hotel);

        Hotel modifiedHotel = hotelService.getAllHotels().get(0);
        modifiedHotel.setName(HotelControllerUnitTest.TestHotelName + " Modified");

        String inputJson = super.mapToJson(modifiedHotel);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(201, status);

        String hotelFromURLResponse = mvcResult.getResponse().getContentAsString();
        Hotel hotelResponse = super.mapFromJson(hotelFromURLResponse, Hotel.class);
        verifyHotelDetails(
                hotelResponse,
                modifiedHotel.getAbout(),
                modifiedHotel.getLocation(),
                modifiedHotel.getName()
        );
    }

    @Test
    public void testDeleteHotel() throws Exception {
        String uri = "/hotel-service/hotel/delete/{hotelID}";

        Hotel hotel = HotelControllerUnitTest.getHotelObject();
        hotelService.saveHotel(hotel);

        Hotel hotelFromService = hotelService.getAllHotels().get(0);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .delete(uri, hotelFromService.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(201, status);

        Assertions.assertEquals(
                0,
                hotelService.getAllHotels().toArray().length,
                "No Hotel object should be present in the DB"
        );
    }

    private static Hotel getHotelObject() {
        Hotel hotel = new Hotel().builder()
                .name(HotelControllerUnitTest.TestHotelName)
                .about(HotelControllerUnitTest.TestHotelAbout)
                .location(HotelControllerUnitTest.TestHotelLocation)
                .build();
        return hotel;
    }

    private static void verifyHotelDetails(Hotel hotelFromURLResponse,
                                           String hotelAbout,
                                           String hotelLocation,
                                           String hotelName) {
        Assert.notNull(
                hotelFromURLResponse,
                "Hotel get from the service should be present."
        );

        Assertions.assertEquals(
                hotelAbout,
                hotelFromURLResponse.getAbout(),
                "Hotel About should be equal to the " + HotelControllerUnitTest.TestHotelAbout
        );

        Assertions.assertEquals(
                hotelLocation,
                hotelFromURLResponse.getLocation(),
                "Hotel Location should be equal to the " + HotelControllerUnitTest.TestHotelLocation
        );

        Assertions.assertEquals(
                hotelName,
                hotelFromURLResponse.getName(),
                "Hotel Name should be equal to the " + HotelControllerUnitTest.TestHotelName
        );
    }

}
