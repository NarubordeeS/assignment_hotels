package com.hotels.service;

import com.hotels.constant.ExceptionConstants;
import com.hotels.constant.RateLimitExceptions;
import com.hotels.model.UsersModel;
import com.hotels.repo.MembersRepo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * Created by narubordeesarnsuwan on 4/24/2016 AD.
 */
@Service
public class RateLimitService {

    @Autowired
    MembersRepo membersRepo;

    @Value("${hotels.rateLimit}")
    private Integer rateLimit;

    private HashMap<String, UsersModel> limitDB = new HashMap<>();
    private HashMap<String, Integer> memberDB = new HashMap<>();

    @PostConstruct
    private void InitMemberDB()
    {
        membersRepo.findAll()
                .forEach(each-> memberDB.put(each.getName(),each.getLimit()));
    }

    final private Integer GLOBAL_LIMIT = 10;
    final private Integer GLOBAL_SUSPEND = 300;

    public void validateKey(String key) throws RateLimitExceptions {
        Integer limitTime = checkMember(key);
        if (limitDB.containsKey(key)) {
            DateTime currentTime = DateTime.now();
            UsersModel users = limitDB.get(key);
            if ((currentTime.compareTo(users.getAvailableTime())) >= 0) {
                limitDB.put(key,new UsersModel(currentTime.plusSeconds(limitTime),false));
            } else {
                //prevent user try to flush requests
                //that cause suspend time increase 5 min per request
                if (users.getSuspend().booleanValue() == false) {
                    users = new UsersModel(users.getAvailableTime().plusSeconds(GLOBAL_SUSPEND), true);
                    limitDB.put(key, users);
                }
                throw new RateLimitExceptions(ExceptionConstants.SUSPEND.getContent()
                        + ". Api will be availabled on "
                        + users.getAvailableTime().toString("yyyy-MM-dd HH:mm:ss"));
            }
        } else {
            limitDB.put(key, new UsersModel(DateTime.now().plusSeconds(limitTime),false));
        }

        return;
    }

    private Integer checkMember(String key) {
        if (memberDB.containsKey(key)){
            return memberDB.get(key);
        }
        else {
            return GLOBAL_LIMIT;
        }
    }
}


