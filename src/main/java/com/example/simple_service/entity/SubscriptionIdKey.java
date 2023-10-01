package com.example.simple_service.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;
@NoArgsConstructor
@Data
public class SubscriptionIdKey implements Serializable {

    private UUID userId;

    private UUID subscriptionId;

}
