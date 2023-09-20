package com.example.simple_service.exception;


public class UserEmailExistException extends RuntimeException {
    public UserEmailExistException(String message) {
        super(message);
    }
}
