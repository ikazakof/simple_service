package com.example.simple_service.exception;


public class LoopSubscriptionException extends RuntimeException {

    public LoopSubscriptionException(String message) {
        super(message);
    }

}
