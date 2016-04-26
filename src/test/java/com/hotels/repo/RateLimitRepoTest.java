package com.hotels.repo;

import com.hotels.constant.RateLimitExceptions;
import com.hotels.model.UsersModel;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.io.FileNotFoundException;

/**
 * Created by narubordeesarnsuwan on 4/26/2016 AD.
 */
public class RateLimitRepoTest {

    @Autowired
    RateLimitRepo rateLimitRepo;

    @Before
    public void setUp() throws FileNotFoundException {
        MockitoAnnotations.initMocks(this);
        this.rateLimitRepo = new RateLimitRepo();
    }

    @Test
    public void shouldGetGlobalLimitAndGlobalSuspendCorrectly(){
        assertEquals(10,this.rateLimitRepo.getGlobalLimit().intValue());
        assertEquals(300,this.rateLimitRepo.getGlobalSuspend().intValue());
    }


    @Test
    public void shouldSaveAndGetValueCorrectly() throws RateLimitExceptions {
        RateLimitRepo rateLimitRepo = new RateLimitRepo();
        String key = "AAAAA";
        DateTime dateTime = DateTime.now().plusSeconds(10);
        rateLimitRepo.saveOrUpdate(key,new UsersModel(dateTime,false));

        UsersModel actualUsersModel = rateLimitRepo.findUserByApiKey(key);
        assertEquals(false, actualUsersModel.getSuspend());
        assertEquals(dateTime, actualUsersModel.getAvailableTime());
    }

    @Test
    public void shouldReturnNullWhenGetNotExistingKeyCorrectly() throws RateLimitExceptions {
        RateLimitRepo rateLimitRepo = new RateLimitRepo();
        String key = "AAAAA";
        DateTime dateTime = DateTime.now().plusSeconds(10);
        rateLimitRepo.saveOrUpdate(key,new UsersModel(dateTime,false));

        UsersModel actualUsersModel = rateLimitRepo.findUserByApiKey("BBBBB");
        assertNull(actualUsersModel);
    }
}
