package com.example.simple_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class SubscriptionDto {

    UUID userId;
    UUID subscriptionId;
    private ZonedDateTime subDate;

}
