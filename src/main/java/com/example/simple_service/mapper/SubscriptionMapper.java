package com.example.simple_service.mapper;

import com.example.simple_service.dto.SubscriptionDto;
import com.example.simple_service.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.ZonedDateTime;

@Mapper(componentModel = "spring", imports = ZonedDateTime.class)
public interface SubscriptionMapper {

    @Mapping(target = "subDate", expression = "java(ZonedDateTime.now())")
    Subscription convertDtoToSubscription(SubscriptionDto subscriptionDto);

    SubscriptionDto convertSubscriptionToDto(Subscription subscription);

}
