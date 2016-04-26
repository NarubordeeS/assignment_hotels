package com.hotels.service;

import com.hotels.constant.ExceptionConstants;
import com.hotels.constant.RateLimitExceptions;
import com.hotels.model.MembersModel;
import com.hotels.model.UsersModel;
import com.hotels.repo.MembersRepo;
import com.hotels.repo.RateLimitRepo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by narubordeesarnsuwan on 4/24/2016 AD.
 */
@Service
public class RateLimitService {

    @Autowired
    MembersRepo membersRepo;

    @Autowired
    RateLimitRepo rateLimitRepo;

    public RateLimitService(){

    }

    public RateLimitService(MembersRepo membersRepo, RateLimitRepo rateLimitRepo){
        this.membersRepo = membersRepo;
        this.rateLimitRepo = rateLimitRepo;
    }

    public void validateKey(String key) throws RateLimitExceptions {
        Integer limitTime = checkMember(key);
        UsersModel user = this.rateLimitRepo.findUserByApiKey(key);
        if (Optional.ofNullable(user).isPresent()) {
            DateTime currentTime = DateTime.now();

            if ((currentTime.compareTo(user.getAvailableTime())) >= 0) {
                this.rateLimitRepo.saveOrUpdate(key,new UsersModel(currentTime.plusSeconds(limitTime),false));
            } else {
                //prevent user try to flush requests
                //that cause suspend time is increased 5 min per request
                if (user.getSuspend().booleanValue() == false) {
                    UsersModel newUser = new UsersModel(user.getAvailableTime().plusSeconds(rateLimitRepo.getGlobalSuspend()), true);
                    this.rateLimitRepo.saveOrUpdate(key, newUser);
                }
                throw new RateLimitExceptions(ExceptionConstants.SUSPEND.getContent()
                        + ". Api will be available on "
                        + user.getAvailableTime().toString("yyyy-MM-dd HH:mm:ss"));
            }
        }
        else {
            this.rateLimitRepo.saveOrUpdate(key, new UsersModel(DateTime.now().plusSeconds(limitTime),false));
        }

        return;
    }

    private Integer checkMember(String key) {
        List<MembersModel> results = this.membersRepo.findByApiKey(key);
        if (results.size() > 0){
            return results.get(0).getLimit();
        }
        else {
            return this.rateLimitRepo.getGlobalLimit();
        }
    }
}


