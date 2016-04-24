package com.hotels.constant;

import lombok.Getter;

/**
 * Created by narubordeesarnsuwan on 4/24/2016 AD.
 */
@Getter
public enum ResponseConstants   {
    SUCCESS("success"),
    NOT_FOUND("not found"),
    FAIL("fail");

    private String content;

    ResponseConstants(String content){
        this.content = content;
    }
}
