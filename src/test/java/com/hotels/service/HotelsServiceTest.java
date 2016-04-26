package com.hotels.service;

/**
 * Created by narubordeesarnsuwan on 4/24/2016 AD.
 */

import com.google.common.collect.Lists;
import com.hotels.constant.RateLimitExceptions;
import com.hotels.model.HotelsModel;
import com.hotels.repo.HotelsRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by narubordeesarnsuwan on 4/24/2016 AD.
 */
@RunWith(MockitoJUnitRunner.class)
public class HotelsServiceTest {
    @Autowired
    HotelsService hotelsService;

    @Mock
    HotelsRepo hotelsRepo;

    @Mock
    RateLimitService rateLimitService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        hotelsService = new HotelsService(hotelsRepo, rateLimitService);

    }

    @Test
    public void shouldCallVerifyKeyOnceAndCallFindAllOnceWhenGetAll() throws Exception  {
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

        when(hotelsRepo.findAll(anyString(),anyString())).thenReturn(results);
        try {
            doNothing().when(rateLimitService).validateKey(anyString());
        } catch (RateLimitExceptions rateLimitExceptions) {
            rateLimitExceptions.printStackTrace();
        }

        try {
            hotelsService.getAll("1","1","1");
        } catch (RateLimitExceptions rateLimitExceptions) {
            rateLimitExceptions.printStackTrace();
        }

        verify(hotelsRepo, Mockito.times(1)).findAll(anyString(),anyString());
        try {
            verify(rateLimitService, Mockito.times(1)).validateKey(anyString());
        } catch (RateLimitExceptions rateLimitExceptions) {
            rateLimitExceptions.printStackTrace();
        }
    }

    @Test
    public void shouldCallVerifyKeyOnceAndCallFindByIdOnceWhenGetAll()  {
        List<HotelsModel> results = Lists.<HotelsModel>newArrayList();
        HotelsModel hotel1 = new HotelsModel();
        hotel1.setCity("Bangkok");
        hotel1.setHotelId(99);
        hotel1.setPrice(new Double("192.00"));
        hotel1.setRoom("Deluxe");
        results.add(hotel1);

        when(hotelsRepo.findHotelByHotelIdAndCityName(anyInt(),anyString())).thenReturn(results);

        try {
            doNothing().when(rateLimitService).validateKey(anyString());
        } catch (RateLimitExceptions rateLimitExceptions) {
            rateLimitExceptions.printStackTrace();
        }

        try {
            hotelsService.getHotelsByHotelId(1,"","");
        } catch (RateLimitExceptions rateLimitExceptions) {
            rateLimitExceptions.printStackTrace();
        }

        verify(hotelsRepo, Mockito.times(1)).findHotelByHotelIdAndCityName(1,"");
        try {
            verify(rateLimitService, Mockito.times(1)).validateKey(anyString());
        } catch (RateLimitExceptions rateLimitExceptions) {
            rateLimitExceptions.printStackTrace();
        }
    }
}
