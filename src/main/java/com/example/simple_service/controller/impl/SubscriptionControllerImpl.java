package com.example.simple_service.controller.impl;

import com.example.simple_service.controller.SubscriptionController;
import com.example.simple_service.dto.SubscriptionDto;
import com.example.simple_service.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SubscriptionControllerImpl implements SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Override
    public ResponseEntity<SubscriptionDto> createSubscription(SubscriptionDto subscriptionDto) {
        return new ResponseEntity<>(subscriptionService.createSubscription(subscriptionDto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SubscriptionDto>> getAllUserSubscriptions(UUID id) {
        return new ResponseEntity<>(subscriptionService.getAllUserSubscription(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteSubscription(SubscriptionDto subscriptionDto) {
        return new ResponseEntity<>(subscriptionService.deleteSubscription(subscriptionDto), HttpStatus.OK);
    }
}
