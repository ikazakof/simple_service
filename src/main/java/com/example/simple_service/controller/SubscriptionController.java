package com.example.simple_service.controller;

import com.example.simple_service.dto.SubscriptionDto;
import com.example.simple_service.utils.WebConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(value = WebConstant.VERSION_URL + "/subscriptions")
public interface SubscriptionController {

    @PostMapping
    ResponseEntity<SubscriptionDto> createSubscription(@RequestBody SubscriptionDto subscriptionDto);

    @GetMapping(value = "/{id}")
    ResponseEntity<List<SubscriptionDto>> getAllUserSubscriptions(@PathVariable UUID id);

    @DeleteMapping
    ResponseEntity<String> deleteSubscription(@RequestBody SubscriptionDto subscriptionDto);

}
