package com.example.springappleapi.Exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(long prodId) {
        super("Invalid prodId: " + prodId);
    }
}
