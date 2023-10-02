package com.example.simple_service.service;

import com.example.simple_service.dto.SubscriptionDto;
import com.example.simple_service.entity.Subscription;
import com.example.simple_service.exception.LoopSubscriptionException;
import com.example.simple_service.exception.SubscriptionAlreadyExistException;
import com.example.simple_service.exception.SubscriptionNotFoundException;
import com.example.simple_service.exception.UserNotFoundException;
import com.example.simple_service.mapper.SubscriptionMapper;
import com.example.simple_service.repository.SubscriptionRepository;
import com.example.simple_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;


    public SubscriptionDto createSubscription(SubscriptionDto subscriptionDto) {
        Subscription subscriptionToSave = subscriptionMapper.convertDtoToSubscription(subscriptionDto);
        checkSubscriptionOption(subscriptionToSave);
        subscriptionRepository.save(subscriptionToSave);
        log.info("Subscriber with id - " + subscriptionToSave.getUserId() + " subscribed to user with id - " + subscriptionToSave.getSubscriptionId());
        return subscriptionMapper.convertSubscriptionToDto(subscriptionToSave);
    }

    private void checkSubscriptionOption(Subscription subscription) {
        if (!userRepository.existsById(subscription.getUserId())) {
            log.error("User with id - " + subscription.getUserId() + " does not exist");
            throw new UserNotFoundException("User with id - " + subscription.getUserId() + " does not exist");
        }
        if (!userRepository.existsById(subscription.getSubscriptionId())) {
            log.error("User with id - " + subscription.getSubscriptionId() + " for subscribe does not exist");
            throw new UserNotFoundException("User with id - " + subscription.getSubscriptionId() + " for subscribe does not exist");
        }
        if (subscription.getUserId().equals(subscription.getSubscriptionId())) {
            log.error("You can't subscribe to yourself");
            throw new LoopSubscriptionException("You can't subscribe to yourself");
        }
        if (subscriptionRepository.existsByUserIdAndSubscriptionId(subscription.getUserId(), subscription.getSubscriptionId())) {
            log.error("This subscription already exist");
            throw new SubscriptionAlreadyExistException("This subscription already exist");
        }
    }

    public List<SubscriptionDto> getAllUserSubscription(UUID id) {
        if (!subscriptionRepository.existsSubscriptionsByUserId(id)) {
            log.error("Subscriptions with userId - " + id + " does not exist");
            throw new SubscriptionNotFoundException("Subscriptions with userId - " + id + " does not exist");
        }
        List<Subscription> subscriptionFromDb = subscriptionRepository.findAllSubscriptionsByUserId(id);
        List<SubscriptionDto> subscriptionDto = subscriptionFromDb.stream()
                .map(subscriptionMapper::convertSubscriptionToDto)
                .toList();
        subscriptionDto.forEach(sub -> log.info("Find subscriptions - " + sub));
        return subscriptionDto;
    }

    public String deleteSubscription(SubscriptionDto subscriptionDto) {
        Subscription subscriptionFromDb = subscriptionMapper.convertDtoToSubscription(subscriptionDto);
        if (!subscriptionRepository.existsByUserIdAndSubscriptionId(subscriptionFromDb.getUserId(), subscriptionFromDb.getSubscriptionId())) {
            log.error("Subscriptions - " + subscriptionDto + " does not exist");
            throw new SubscriptionNotFoundException("Subscriptions - " + subscriptionDto + " does not exist");
        }
        subscriptionRepository.deleteByUserIdAndSubscriptionId(subscriptionFromDb.getUserId(), subscriptionFromDb.getSubscriptionId());
        log.info("Subscription - " + subscriptionDto + " successfully deleted");
        return "Subscription - " + subscriptionDto + " successfully deleted";
    }
}
