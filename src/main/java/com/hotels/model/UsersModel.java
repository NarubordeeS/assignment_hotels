package com.hotels.model;

import lombok.Data;
import lombok.Getter;
import org.joda.time.DateTime;

/**
 * Created by narubordeesarnsuwan on 4/24/2016 AD.
 */
@Getter
public class UsersModel {
    DateTime availableTime;
    Boolean suspend;

    public UsersModel(){
        suspend = false;
    }

    public UsersModel(DateTime availableTime, Boolean suspend){
        this.availableTime = availableTime;
        this.suspend = suspend;
    }
}
