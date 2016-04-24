package com.hotels.service;

import com.hotels.constant.RateLimitExceptions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by narubordeesarnsuwan on 4/23/2016 AD.
 */
public class RateLimitServiceTest {
    @Autowired
    RateLimitService rateLimitService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        rateLimitService = new RateLimitService();

    }

    @Test
    public void shouldValidateKeySuccess() throws RateLimitExceptions {
        String apiKey = "AAAA";
        rateLimitService.validateKey(apiKey);
        assert(true);
    }

    @Test
    public void shouldValidateKeySuccessWhenValidateDifferentUsers() throws RateLimitExceptions {
        String apiKey1 = "AAAA";
        String apiKey2 = "BBBB";
        rateLimitService.validateKey(apiKey1);
        rateLimitService.validateKey(apiKey2);
        assert(true);

    }
    @Test(expected = RateLimitExceptions.class)
    public void shouldThrowExceptionWhenExceedLimit() throws RateLimitExceptions {
        String apiKey = "AAAA";
        rateLimitService.validateKey(apiKey);
        rateLimitService.validateKey(apiKey);
        assert(false);


    }

}
