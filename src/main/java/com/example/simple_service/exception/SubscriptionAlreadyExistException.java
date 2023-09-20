package com.example.simple_service.exception;


public class SubscriptionAlreadyExistException extends RuntimeException {

    public SubscriptionAlreadyExistException(String message) {
        super(message);
    }

}
