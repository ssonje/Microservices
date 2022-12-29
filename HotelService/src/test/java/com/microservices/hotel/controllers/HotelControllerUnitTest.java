package com.microservices.hotel.controllers;

import com.microservices.hotel.constants.HotelControllerTestAPIResponseConstants;
import com.microservices.hotel.entities.Hotel;
import com.microservices.hotel.payloads.APIResponse;
import com.microservices.hotel.services.HotelService;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class HotelControllerUnitTest extends AbstractTest {

    private static String TestHotelID = UUID.randomUUID().toString();
    private static String TestHotelName = "Test Hotel Name";
    private static String TestHotelAbout = "Test Hotel About";
    private static String TestHotelLocation = "Test Hotel Location";

    @Autowired
    private HotelService hotelService;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        Hotel hotel = getHotelObject();
        hotelService.saveHotelWithID(hotel);
    }

    @AfterEach
    public void tearDown() {
        hotelService.deleteAllHotels();
    }

    @Test
    public void testGetHotelWithID() throws Exception {
        String getHotelWithIDURLString = "/hotel-service/hotel/{hotelID}";

        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.get(getHotelWithIDURLString, TestHotelID).accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        String hotelFromURLResponse = mvcResult.getResponse().getContentAsString();
        Hotel hotelResponse = super.mapFromJson(hotelFromURLResponse, Hotel.class);
        verifyHotelDetails(hotelResponse, TestHotelAbout, TestHotelLocation, TestHotelName);
    }

    @Test
    public void testGetAllHotels() throws Exception {
        String getHotelsURLString = "/hotel-service/hotels";

        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.get(getHotelsURLString).accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        Hotel[] hotelsList = super.mapFromJson(content, Hotel[].class);
        Assertions.assertTrue(hotelsList.length > 0);
    }

    @Test
    public void testSaveHotel() throws Exception {
        String saveHotelURLString = "/hotel-service/hotel/new";

        Hotel hotel = getHotelObject();
        String inputJson = super.mapToJson(hotel);

        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.post(saveHotelURLString).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
            .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        APIResponse apiResponse = super.mapFromJson(response, APIResponse.class);

        // Verify API response details
        verifyAPIResponse(apiResponse, true, HttpStatus.CREATED, HotelControllerTestAPIResponseConstants.ADD_HOTEL_SUCCESS);
    }

    @Test
    public void testModifyHotel() throws Exception {
        String modifyHotelURLString = "/hotel-service/hotel/modify";

        Hotel modifiedHotel = hotelService.getHotelFromID(TestHotelID);
        modifiedHotel.setName(TestHotelName + " Modified");

        String inputJson = super.mapToJson(modifiedHotel);
        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.put(modifyHotelURLString).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
            .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        APIResponse apiResponse = super.mapFromJson(response, APIResponse.class);

        // Verify API response details
        verifyAPIResponse(apiResponse, true, HttpStatus.OK, HotelControllerTestAPIResponseConstants.MODIFIED_HOTEL_SUCCESS);
    }

    @Test
    public void testDeleteHotel() throws Exception {
        String deleteHotelURLString = "/hotel-service/hotel/delete/{hotelID}";

        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.delete(deleteHotelURLString, TestHotelID).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        APIResponse apiResponse = super.mapFromJson(response, APIResponse.class);

        // Verify API response details
        verifyAPIResponse(apiResponse, true, HttpStatus.OK, HotelControllerTestAPIResponseConstants.DELETE_HOTEL_SUCCESS);
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

    private static void verifyHotelDetails(Hotel hotelFromURLResponse, String hotelAbout, String hotelLocation, String hotelName) {
        Assert.notNull(
            hotelFromURLResponse,
            "Hotel get from the response should not be nil."
        );

        Assertions.assertEquals(
            hotelAbout,
            hotelFromURLResponse.getAbout(),
            "Hotel About from URL response " + hotelFromURLResponse.getAbout() + " should be equal to the " + hotelAbout
        );

        Assertions.assertEquals(
            hotelLocation,
            hotelFromURLResponse.getLocation(),
            "Hotel Location from URL response " + hotelFromURLResponse.getLocation() + " should be equal to the " + hotelAbout
        );

        Assertions.assertEquals(
            hotelName,
            hotelFromURLResponse.getName(),
            "Hotel Name from URL response " + hotelFromURLResponse.getName() + " should be equal to the " + hotelName
        );
    }

    private static void verifyAPIResponse(APIResponse apiResponse,
        Boolean expectedResponseStatus,
        HttpStatus expectedHttpStatus,
        String expectedMessage) {
        Assertions.assertEquals(expectedResponseStatus, apiResponse.getResponseStatus());
        Assertions.assertEquals(expectedHttpStatus, apiResponse.getHttpStatus());
        Assertions.assertEquals(expectedMessage, apiResponse.getMessage());
    }
}
