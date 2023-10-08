package com.example.simple_service.controllerAdvice;

import com.example.simple_service.controllerAdvice.errorResponse.BaseErrorResponse;
import com.example.simple_service.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<BaseErrorResponse> handleUserNotFoundException(UserNotFoundException e){
        BaseErrorResponse baseErrorResponse = new BaseErrorResponse(e.getMessage());
        return new ResponseEntity<>(baseErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<BaseErrorResponse> handleSubscriptionNotFoundException(SubscriptionNotFoundException e){
        BaseErrorResponse baseErrorResponse = new BaseErrorResponse(e.getMessage());
        return new ResponseEntity<>(baseErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LoopSubscriptionException.class)
    public ResponseEntity<BaseErrorResponse> handleSubscriptionException(LoopSubscriptionException e){
        BaseErrorResponse baseErrorResponse = new BaseErrorResponse(e.getMessage());
        return new ResponseEntity<>(baseErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserEmailExistException.class)
    public ResponseEntity<BaseErrorResponse> handleUserEmailExistException(UserEmailExistException e){
        BaseErrorResponse baseErrorResponse = new BaseErrorResponse(e.getMessage());
        return new ResponseEntity<>(baseErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserPhoneExistException.class)
    public ResponseEntity<BaseErrorResponse> handleUserPhoneExistException(UserPhoneExistException e){
        BaseErrorResponse baseErrorResponse = new BaseErrorResponse(e.getMessage());
        return new ResponseEntity<>(baseErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SubscriptionAlreadyExistException.class)
    public ResponseEntity<BaseErrorResponse> handleSubscriptionAlreadyExistException(SubscriptionAlreadyExistException e){
        BaseErrorResponse baseErrorResponse = new BaseErrorResponse(e.getMessage());
        return new ResponseEntity<>(baseErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<BaseErrorResponse> handleCityNotFoundException(CityNotFoundException e){
        BaseErrorResponse baseErrorResponse = new BaseErrorResponse(e.getMessage());
        return new ResponseEntity<>(baseErrorResponse, HttpStatus.NOT_FOUND);
    }
}
