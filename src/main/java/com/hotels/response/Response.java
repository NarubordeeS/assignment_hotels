package com.hotels.response;

import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by narubordeesarnsuwan on 4/24/2016 AD.
 */
@Data
public class Response {

    final String message;
    final Object data;

    public Response(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public Response(String message) {
        this.message = message;
        this.data = "";
    }

    public HttpEntity<Response> build(HttpStatus status) {
        return new ResponseEntity<Response>(new Response(this.message, this.data), status);
    }
}