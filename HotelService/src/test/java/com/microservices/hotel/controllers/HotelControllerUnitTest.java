package com.microservices.hotel.controllers;

import com.microservices.hotel.entities.Hotel;
import com.microservices.hotel.helpers.HotelUnitTestHelper;
import com.microservices.hotel.helpers.constants.HotelControllerTestAPIResponseConstants;
import com.microservices.hotel.payloads.APIResponse;
import com.microservices.hotel.services.HotelService;

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

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class HotelControllerUnitTest extends AbstractTest {

    @Autowired
    private HotelService hotelService;

    // MARK: - Lifecycle Methods

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        Hotel hotel = HotelUnitTestHelper.createHotelObject();
        hotelService.saveHotelWithID(hotel);
    }

    @AfterEach
    public void tearDown() {
        hotelService.deleteAllHotels();
    }

    // MARK: - Tests

    @Test
    public void testGetHotelWithID() throws Exception {
        String getHotelWithIDURLString = "/hotel-service/hotel/{hotelID}";

        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders
                .get(getHotelWithIDURLString, HotelUnitTestHelper.TestHotelID)
                .accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        String hotelFromURLResponse = mvcResult.getResponse().getContentAsString();
        Hotel hotelResponse = super.mapFromJson(hotelFromURLResponse, Hotel.class);
        HotelUnitTestHelper.verifyHotelDetails(hotelResponse);
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

        Hotel hotel = HotelUnitTestHelper.createHotelObject();
        String inputJson = super.mapToJson(hotel);

        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.post(saveHotelURLString).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
            .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        APIResponse apiResponse = super.mapFromJson(response, APIResponse.class);

        // Verify API response details
        HotelUnitTestHelper.verifyAPIResponse(apiResponse, true, HttpStatus.CREATED, HotelControllerTestAPIResponseConstants.ADD_HOTEL_SUCCESS);
    }

    @Test
    public void testModifyHotel() throws Exception {
        String modifyHotelURLString = "/hotel-service/hotel/modify";

        Hotel modifiedHotel = hotelService.getHotelFromID(HotelUnitTestHelper.TestHotelID);
        modifiedHotel.setName(HotelUnitTestHelper.TestHotelName + " Modified");

        String inputJson = super.mapToJson(modifiedHotel);
        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.put(modifyHotelURLString).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
            .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        APIResponse apiResponse = super.mapFromJson(response, APIResponse.class);

        // Verify API response details
        HotelUnitTestHelper.verifyAPIResponse(apiResponse, true, HttpStatus.OK, HotelControllerTestAPIResponseConstants.MODIFIED_HOTEL_SUCCESS);
    }

    @Test
    public void testDeleteHotel() throws Exception {
        String deleteHotelURLString = "/hotel-service/hotel/delete/{hotelID}";

        MvcResult mvcResult = mvc
            .perform(MockMvcRequestBuilders.delete(deleteHotelURLString, HotelUnitTestHelper.TestHotelID).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        APIResponse apiResponse = super.mapFromJson(response, APIResponse.class);

        // Verify API response details
        HotelUnitTestHelper.verifyAPIResponse(apiResponse, true, HttpStatus.OK, HotelControllerTestAPIResponseConstants.DELETE_HOTEL_SUCCESS);
    }

}
