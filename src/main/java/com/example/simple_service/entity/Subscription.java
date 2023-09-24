package com.example.simple_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@IdClass(SubscriptionIdKey.class)
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @Column(name = "user_id")
    UUID userId;

    @Id
    @Column(name = "subscription_id")
    UUID subscriptionId;

    @Column (name = "subscription_date")
    private ZonedDateTime subDate;

}
