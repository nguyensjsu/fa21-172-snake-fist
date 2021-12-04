package com.example.springappleapi.Exceptions;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(long cartId) {
        super("Invalid cartId: " + cartId);
    }
}
