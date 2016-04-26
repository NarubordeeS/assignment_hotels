package com.hotels.service;

import com.hotels.constant.RateLimitExceptions;
import com.hotels.model.MembersModel;
import com.hotels.model.UsersModel;
import com.hotels.repo.HotelsRepo;
import com.hotels.repo.MembersRepo;
import com.hotels.repo.RateLimitRepo;
import org.joda.time.DateTime;
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

    @Mock
    RateLimitRepo rateLimitRepo;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.rateLimitService = new RateLimitService(this.membersRepo, this.rateLimitRepo);
        List<MembersModel> mockMember = new ArrayList<>();
        MembersModel membersModel = new MembersModel();
        membersModel.setName("AAAA");
        membersModel.setLimit(1);
        mockMember.add(membersModel);

        UsersModel usersModel = new UsersModel(DateTime.now(),false);

        when(this.membersRepo.findByApiKey("AAAA")).thenReturn(mockMember);
        when(this.rateLimitRepo.findUserByApiKey("AAAA")).thenReturn(usersModel);
    }

    @Test
    public void shouldValidateKeySuccess() throws RateLimitExceptions {
        String apiKey = "AAAA";
        this.rateLimitService.validateKey(apiKey);
        assert(true);
    }

    @Test
    public void shouldValidateKeySuccessWhenValidateDifferentUsers() throws RateLimitExceptions {
        String apiKey1 = "AAAA";
        String apiKey2 = "BBBB";
        this.rateLimitService.validateKey(apiKey1);
        this.rateLimitService.validateKey(apiKey2);
        assert(true);

    }

    @Test
    public void shouldValidateKeyPassedWhenIntevalIsLessThanLimit() throws RateLimitExceptions, InterruptedException {
        String apiKey = "AAAA";
        this.rateLimitService.validateKey(apiKey);
        Thread.sleep(1000);
        this.rateLimitService.validateKey(apiKey);
        assert(true);

    }

    @Test(expected = RateLimitExceptions.class)
    public void shouldThrowExceptionWhenExceedLimit() throws RateLimitExceptions {
        UsersModel usersModel = new UsersModel(DateTime.now().plusMinutes(1),false);
        when(this.rateLimitRepo.findUserByApiKey("AAAA")).thenReturn(usersModel);
        String apiKey = "AAAA";
        this.rateLimitService.validateKey(apiKey);
        this.rateLimitService.validateKey(apiKey);
        assert(false);
    }

}
