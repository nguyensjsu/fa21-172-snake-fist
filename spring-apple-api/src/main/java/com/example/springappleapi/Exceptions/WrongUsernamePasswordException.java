package com.example.springappleapi.Exceptions;

public class WrongUsernamePasswordException extends RuntimeException {
    public WrongUsernamePasswordException() {
        super("{\"error\":\"Incorrect username or password\"}");
    }
}
