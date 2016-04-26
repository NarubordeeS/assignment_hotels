package com.hotels.controller;

import com.hotels.constant.ExceptionConstants;
import com.hotels.constant.RateLimitExceptions;
import com.hotels.model.HotelsModel;
import com.hotels.repo.HotelsRepo;
import com.hotels.response.Response;
import com.hotels.constant.ResponseConstants;
import com.hotels.service.HotelsService;
import com.hotels.service.RateLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by narubordeesarnsuwan on 4/23/2016 AD.
 */
@RestController
@RequestMapping(value = "/api/v1")
public class HotelsController {

    @Autowired
    HotelsService hotelsService;

    @RequestMapping(value = "/hotels/{key}", method = RequestMethod.GET)
    HttpEntity<Response> getHotels(@PathVariable String key,
                                      @RequestParam(required = false, value = "sort") String sortKey,
                                      @RequestParam(required = false, value = "direction") String direction,
                                      @RequestParam(required = false, value = "hotel_id") Integer hotelId,
                                      @RequestParam(required = false, value = "city", defaultValue = "") String cityName) {

        List<HotelsModel> results = null;
        try {
            if(hotelId == null && "".equals(cityName)) {
                results = hotelsService.getAll(sortKey,direction,key);
            }
            else {
                results = hotelsService.getHotelsByHotelId(hotelId, cityName, key);
            }
        } catch (RateLimitExceptions rateLimitExceptions) {
            return new Response(rateLimitExceptions.getMessage()).build(HttpStatus.BAD_REQUEST);
        }

        return new Response(ResponseConstants.SUCCESS.getContent(),
                results).build(HttpStatus.OK);
    }
}