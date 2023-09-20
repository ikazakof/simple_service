package com.example.simple_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @Column(name = "user_id")
    UUID userId;

    @Column(name = "subscription_id")
    UUID subscriptionId;

    @Column (name = "subscription_date")
    private ZonedDateTime subDate;

}
