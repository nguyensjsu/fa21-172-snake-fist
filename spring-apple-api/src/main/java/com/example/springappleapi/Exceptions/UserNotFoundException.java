package com.example.springappleapi.Exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(long userId) {
        super("Invalid userId: " + userId);
    }
}
