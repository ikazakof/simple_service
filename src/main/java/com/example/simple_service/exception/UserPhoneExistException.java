package com.example.simple_service.exception;


public class UserPhoneExistException extends RuntimeException {
    public UserPhoneExistException(String message) {
        super(message);
    }
}
