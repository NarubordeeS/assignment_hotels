package com.hotels.constant;

import lombok.Getter;

/**
 * Created by narubordeesarnsuwan on 4/24/2016 AD.
 */
@Getter
public class RateLimitExceptions extends Exception{

    public RateLimitExceptions(){

    }

    public RateLimitExceptions(String message) {
        super(message);
    }

}

