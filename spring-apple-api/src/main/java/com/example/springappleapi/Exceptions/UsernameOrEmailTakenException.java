package com.example.springappleapi.Exceptions;

public class UsernameOrEmailTakenException extends RuntimeException {
    public UsernameOrEmailTakenException() {
        super("{\"error\":\"Username or Email taken\"}");
    }
}
