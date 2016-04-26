package com.hotels.repo;

import com.hotels.constant.RateLimitExceptions;
import com.hotels.model.UsersModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by narubordeesarnsuwan on 4/26/2016 AD.
 */
@Component
public class RateLimitRepo {

    private HashMap<String, UsersModel> limitDB = new HashMap<>();

    public RateLimitRepo() {

    }

    //Able to configure via config file
    @Value("${spring.hotels.rateLimit}")
    final private Integer GLOBAL_LIMIT = 10;

    final private Integer GLOBAL_SUSPEND = 300;

    public Integer getGlobalLimit() {
        return this.GLOBAL_LIMIT;
    }

    public Integer getGlobalSuspend() {
        return this.GLOBAL_SUSPEND;
    }


    public void saveOrUpdate(String key,UsersModel usersModel) throws RateLimitExceptions {
        this.limitDB.put(key,usersModel);
    }

    public UsersModel findUserByApiKey(String key){
        if (this.limitDB.containsKey(key)) {
            return this.limitDB.get(key);
        }
        return null;
    }
}
