package com.hotels.service;

import com.hotels.constant.RateLimitExceptions;
import com.hotels.model.MembersModel;
import com.hotels.repo.HotelsRepo;
import com.hotels.repo.MembersRepo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by narubordeesarnsuwan on 4/23/2016 AD.
 */
public class RateLimitServiceTest {
    @Autowired
    RateLimitService rateLimitService;

    @Mock
    MembersRepo membersRepo;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        rateLimitService = new RateLimitService(membersRepo);
        List<MembersModel> mockMember = new ArrayList<>();
        MembersModel membersModel = new MembersModel();
        membersModel.setName("AAAA");
        membersModel.setLimit(1);
        mockMember.add(membersModel);
        when(membersRepo.findByApiKey("AAAA")).thenReturn(mockMember);
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

    @Test
    public void shouldValidateKeyPassedWhenIntevalIsLessThanLimit() throws RateLimitExceptions, InterruptedException {
        String apiKey = "AAAA";
        rateLimitService.validateKey(apiKey);
        Thread.sleep(1000);
        rateLimitService.validateKey(apiKey);
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
