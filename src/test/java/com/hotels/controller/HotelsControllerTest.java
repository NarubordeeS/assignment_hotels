package com.hotels.controller;

import com.hotels.model.HotelsModel;
import com.hotels.repo.HotelsRepo;
import com.google.common.collect.Lists;
import com.hotels.service.HotelsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by narubordeesarnsuwan on 4/24/2016 AD.
 */
public class HotelsControllerTest {
    MockMvc mvc;

    @InjectMocks
    HotelsController controller;

    @Mock
    HotelsService hotelsService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldCallGetAllOnetimeAndReturnArrayWithSizeOne() throws Exception {
        List<HotelsModel> results = Lists.<HotelsModel>newArrayList();
        HotelsModel hotel1 = new HotelsModel();
        hotel1.setCity("Bangkok");
        hotel1.setHotelId(99);
        hotel1.setPrice(new Double("192.00"));
        hotel1.setRoom("Deluxe");

        HotelsModel hotel2 = new HotelsModel();
        hotel1.setCity("Japan");
        hotel1.setHotelId(100);
        hotel1.setPrice(new Double("380.00"));
        hotel1.setRoom("Single Bed");

        results.add(hotel1);
        results.add(hotel2);

        when(hotelsService.getAll(anyString(),anyString(),anyString())).thenReturn(results);

        mvc.perform(get("/api/v1/hotels/AAAAA"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.message", is("success")))
                .andExpect(status().isOk());

        verify(hotelsService, Mockito.times(1)).getAll(anyString(),anyString(),anyString());
        verify(hotelsService, Mockito.times(0)).getHotelsByHotelId(anyInt(),anyString(),anyString(),anyString(),anyString());
    }

    @Test
    public void shouldCallGetByIdOnetimeAndReturnArrayWithSizeOne() throws Exception {
        List<HotelsModel> results = Lists.<HotelsModel>newArrayList();
        HotelsModel hotel1 = new HotelsModel();
        hotel1.setCity("Bangkok");
        hotel1.setHotelId(99);
        hotel1.setPrice(new Double("192.00"));
        hotel1.setRoom("Deluxe");
        results.add(hotel1);


        when(hotelsService.getHotelsByHotelId(anyInt(),anyString(),anyString(),anyString(),anyString())).thenReturn(results);

        mvc.perform(get("/api/v1/hotels/AAAAA?id=1"))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.message", is("success")))
                .andExpect(status().isOk());

        verify(hotelsService, Mockito.times(1)).getHotelsByHotelId(anyInt(),anyString(),anyString(),anyString(),anyString());
        verify(hotelsService, Mockito.times(0)).getAll(anyString(),anyString(),anyString());
    }
}

