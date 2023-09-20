package com.example.simple_service.repository;

import com.example.simple_service.entity.Subscription;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.UUID;

@Repository
@Table(name = "subscriptions")
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    @Modifying
    @Query(value = "INSERT INTO users_scheme.subscriptions (user_id, subscription_id, subscription_date) " +
            "VALUES (:id, :sub_id, :sub_date)", nativeQuery = true)
    void insertInTable(@Param("id") UUID id, @Param("sub_id") UUID subId, @Param("sub_date") ZonedDateTime subDate);

    boolean existsSubscriptionsByUserId(UUID id);

    boolean existsByUserIdAndSubscriptionId(UUID userId, UUID SubscriptionId);

    @Modifying
    @Query(value = "DELETE FROM users_scheme.subscriptions WHERE user_id=:user_id AND subscription_id=:sub_id", nativeQuery = true)
    void deleteByUserIdAndSubscriptionId(@Param("user_id") UUID userId, @Param("sub_id") UUID SubscriptionId);

}
