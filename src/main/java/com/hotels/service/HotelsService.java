package com.hotels.service;

import com.hotels.constant.RateLimitExceptions;
import com.hotels.model.HotelsModel;
import com.hotels.repo.HotelsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by narubordeesarnsuwan on 4/24/2016 AD.
 */
@Service
public class HotelsService {

    @Autowired
    private HotelsRepo hotelsRepo;

    @Autowired
    private RateLimitService rateLimitService;

    public HotelsService() {
    }
    public HotelsService(HotelsRepo hotelsRepo, RateLimitService rateLimitService) {
        this.hotelsRepo = hotelsRepo;
        this.rateLimitService = rateLimitService;
    }

    public List<HotelsModel> getHotelsByHotelId(Integer hotelId, String cityName, String key, String sortKey, String direction) throws RateLimitExceptions {
        this.rateLimitService.validateKey(key);
        return this.hotelsRepo.findHotelByHotelIdAndCityName(hotelId,cityName, sortKey, direction);
    }

    public List<HotelsModel> getAll(String sortKey, String direction,String key) throws RateLimitExceptions {
        this.rateLimitService.validateKey(key);
        return this.hotelsRepo.findAll(sortKey,direction);
    }
}
