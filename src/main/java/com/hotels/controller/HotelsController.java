package com.hotels.controller;

import com.hotels.constant.RateLimitExceptions;
import com.hotels.constant.ResponseConstants;
import com.hotels.model.HotelsModel;
import com.hotels.response.Response;
import com.hotels.service.HotelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
                                      @RequestParam(required = false, value = "id") Integer hotelId,
                                      @RequestParam(required = false, value = "city", defaultValue = "") String cityName) {

        List<HotelsModel> results = null;
        try {
            if(
            !Optional.ofNullable(hotelId).isPresent()
                    && "".equals(cityName)) {
                results = this.hotelsService.getAll(sortKey,direction,key);
            }
            else {
                results = this.hotelsService.getHotelsByHotelId(hotelId, cityName, key, sortKey, direction);
            }
        } catch (RateLimitExceptions rateLimitExceptions) {
            return new Response(rateLimitExceptions.getMessage()).build(HttpStatus.BAD_REQUEST);
        }

        return new Response(ResponseConstants.SUCCESS.getContent(),
                results).build(HttpStatus.OK);
    }
}