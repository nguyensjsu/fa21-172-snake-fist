package com.example.springappleapi.Exceptions;

public class DeletePublishedProductException extends RuntimeException {
    public DeletePublishedProductException(long prodId) {
        super("Cannot delete prodId: " + prodId);
    }
}
