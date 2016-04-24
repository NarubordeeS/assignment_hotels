package com.hotels.constant;

import lombok.Getter;

/**
 * Created by narubordeesarnsuwan on 4/24/2016 AD.
 */
@Getter
public enum ExceptionConstants {
    SUSPEND("Exceed rate limit, Suspend for 5 minute");

    private String content;

    ExceptionConstants(String content){
        this.content = content;
    }
}
