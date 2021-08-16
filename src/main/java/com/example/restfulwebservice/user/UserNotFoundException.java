package com.example.restfulwebservice.user;

// 2XX -> OK
// 4XX -> Client Error
// 5XX -> Server Error
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
