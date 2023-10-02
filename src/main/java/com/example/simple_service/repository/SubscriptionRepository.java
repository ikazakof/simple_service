package com.example.simple_service.repository;

import com.example.simple_service.entity.Subscription;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@Table(name = "subscriptions")
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    boolean existsSubscriptionsByUserId(UUID id);
    @Query(value = "SELECT * FROM users_scheme.subscriptions WHERE user_id=:user_id", nativeQuery = true)
    List<Subscription> findAllSubscriptionsByUserId(@Param("user_id") UUID userId);
    boolean existsByUserIdAndSubscriptionId(UUID userId, UUID SubscriptionId);

    @Modifying
    @Query(value = "DELETE FROM users_scheme.subscriptions WHERE user_id=:user_id AND subscription_id=:sub_id", nativeQuery = true)
    void deleteByUserIdAndSubscriptionId(@Param("user_id") UUID userId, @Param("sub_id") UUID SubscriptionId);

}
