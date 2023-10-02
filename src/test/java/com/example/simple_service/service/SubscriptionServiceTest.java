package com.example.simple_service.service;

import com.example.simple_service.dto.SubscriptionDto;
import com.example.simple_service.entity.Subscription;
import com.example.simple_service.exception.LoopSubscriptionException;
import com.example.simple_service.exception.SubscriptionAlreadyExistException;
import com.example.simple_service.exception.SubscriptionNotFoundException;
import com.example.simple_service.exception.UserNotFoundException;
import com.example.simple_service.mapper.SubscriptionMapper;
import com.example.simple_service.mapper.SubscriptionMapperImpl;
import com.example.simple_service.repository.SubscriptionRepository;
import com.example.simple_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

/**
 * Тестирование функционала {@link SubscriptionService}.
 */
@ExtendWith({MockitoExtension.class})
@DisplayName("Testing subscription service functionality.")
class SubscriptionServiceTest {

    private final UUID testUserUUID = UUID.fromString("4416e9c4-aa48-4e56-b8af-1e57b6fbfae9");
    private final UUID testSubscriptionUUID = UUID.fromString("46ec7d63-86e0-4e44-b541-b5279290c29e");
    private final UUID testSecondUserUUID = UUID.fromString("c25bdb85-415e-4453-b478-d0145776568a");
    private final ZonedDateTime testZonedDateTime = ZonedDateTime.parse("2023-10-01T09:34:56+00:00");

    private SubscriptionService subscriptionService;
    @Mock
    private UserRepository userRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;
    private SubscriptionMapper subscriptionMapper;

    @BeforeEach
    public void init() {
        subscriptionMapper = new SubscriptionMapperImpl();
        subscriptionService = new SubscriptionService(subscriptionRepository, userRepository, subscriptionMapper);
    }

    @Test
    @DisplayName("Create a subscription. Should be successful")
    void createSubscription() {
        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
             MockedStatic<ZonedDateTime> zonedDateTimeMock = mockStatic(ZonedDateTime.class)) {

            uuidMock.when(UUID::randomUUID).thenReturn(testUserUUID);
            UUID userUUID = UUID.randomUUID();
            uuidMock.when(UUID::randomUUID).thenReturn(testSubscriptionUUID);
            UUID subscriptionUUID = UUID.randomUUID();
            zonedDateTimeMock.when(ZonedDateTime::now).thenReturn(testZonedDateTime);

            SubscriptionDto subscriptionDto = new SubscriptionDto();
            subscriptionDto.setUserId(userUUID);
            subscriptionDto.setSubscriptionId(subscriptionUUID);

            Subscription subscriptionFromDto = new Subscription();
            subscriptionFromDto.setUserId(userUUID);
            subscriptionFromDto.setSubscriptionId(subscriptionUUID);
            subscriptionFromDto.setSubDate(ZonedDateTime.now());

            SubscriptionDto savedSubscriptionResultDto = new SubscriptionDto();
            savedSubscriptionResultDto.setUserId(userUUID);
            savedSubscriptionResultDto.setSubscriptionId(subscriptionUUID);
            savedSubscriptionResultDto.setSubDate(ZonedDateTime.now());

            when(userRepository.existsById(subscriptionFromDto.getUserId())).thenReturn(true);
            when(userRepository.existsById(subscriptionFromDto.getSubscriptionId())).thenReturn(true);
            when(subscriptionRepository.existsByUserIdAndSubscriptionId(subscriptionFromDto.getUserId(), subscriptionFromDto
                    .getSubscriptionId())).thenReturn(false);

            when(subscriptionRepository.save(subscriptionFromDto)).thenReturn(subscriptionFromDto);

            SubscriptionDto resultSubscriptionDtoFromService = subscriptionService.createSubscription(subscriptionDto);

            assertNotNull(resultSubscriptionDtoFromService);
            assertEquals(savedSubscriptionResultDto.getUserId(), resultSubscriptionDtoFromService.getUserId());
            assertEquals(savedSubscriptionResultDto.getSubscriptionId(), resultSubscriptionDtoFromService
                    .getSubscriptionId());
            assertEquals(savedSubscriptionResultDto.getSubDate(), resultSubscriptionDtoFromService.getSubDate());

        }
    }

    @Test
    @DisplayName("Create a subscription but userId does not exist. Should be throw exception")
    void createSubscriptionUserIdNotExist_thenFail() {
        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
             MockedStatic<ZonedDateTime> zonedDateTimeMock = mockStatic(ZonedDateTime.class)) {

            uuidMock.when(UUID::randomUUID).thenReturn(testUserUUID);
            UUID userUUID = UUID.randomUUID();
            uuidMock.when(UUID::randomUUID).thenReturn(testSubscriptionUUID);
            UUID subscriptionUUID = UUID.randomUUID();
            zonedDateTimeMock.when(ZonedDateTime::now).thenReturn(testZonedDateTime);

            SubscriptionDto subscriptionDto = new SubscriptionDto();
            subscriptionDto.setUserId(userUUID);
            subscriptionDto.setSubscriptionId(subscriptionUUID);

            Subscription subscriptionFromDto = new Subscription();
            subscriptionFromDto.setUserId(userUUID);
            subscriptionFromDto.setSubscriptionId(subscriptionUUID);
            subscriptionFromDto.setSubDate(ZonedDateTime.now());

            when(userRepository.existsById(subscriptionFromDto.getUserId())).thenReturn(false)
                    .thenThrow(UserNotFoundException.class);

            Executable result = () -> subscriptionService.createSubscription(subscriptionDto);

            assertThrows(UserNotFoundException.class, result);
        }

    }

    @Test
    @DisplayName("Create a subscription but subscriptionId does not exist. Should be throw exception")
    void createSubscriptionSubscriptionIdNotExist_thenFail() {
        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
             MockedStatic<ZonedDateTime> zonedDateTimeMock = mockStatic(ZonedDateTime.class)) {

            uuidMock.when(UUID::randomUUID).thenReturn(testUserUUID);
            UUID userUUID = UUID.randomUUID();
            uuidMock.when(UUID::randomUUID).thenReturn(testSubscriptionUUID);
            UUID subscriptionUUID = UUID.randomUUID();
            zonedDateTimeMock.when(ZonedDateTime::now).thenReturn(testZonedDateTime);

            SubscriptionDto subscriptionDto = new SubscriptionDto();
            subscriptionDto.setUserId(userUUID);
            subscriptionDto.setSubscriptionId(subscriptionUUID);

            Subscription subscriptionFromDto = new Subscription();
            subscriptionFromDto.setUserId(userUUID);
            subscriptionFromDto.setSubscriptionId(subscriptionUUID);
            subscriptionFromDto.setSubDate(ZonedDateTime.now());

            when(userRepository.existsById(subscriptionFromDto.getUserId())).thenReturn(true);
            when(userRepository.existsById(subscriptionFromDto.getSubscriptionId())).thenReturn(false)
                    .thenThrow(UserNotFoundException.class);

            Executable result = () -> subscriptionService.createSubscription(subscriptionDto);

            assertThrows(UserNotFoundException.class, result);
        }

    }

    @Test
    @DisplayName("Create a subscription but userId and subscriptionId equals. Should be throw exception")
    void createSubscriptionUserIdAndSubscriptionIdEquals_thenFail() {
        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
             MockedStatic<ZonedDateTime> zonedDateTimeMock = mockStatic(ZonedDateTime.class)) {

            uuidMock.when(UUID::randomUUID).thenReturn(testUserUUID);
            UUID userUUID = UUID.randomUUID();
            zonedDateTimeMock.when(ZonedDateTime::now).thenReturn(testZonedDateTime);

            SubscriptionDto subscriptionDto = new SubscriptionDto();
            subscriptionDto.setUserId(userUUID);
            subscriptionDto.setSubscriptionId(userUUID);

            Subscription subscriptionFromDto = new Subscription();
            subscriptionFromDto.setUserId(userUUID);
            subscriptionFromDto.setSubscriptionId(userUUID);
            subscriptionFromDto.setSubDate(ZonedDateTime.now());

            when(userRepository.existsById(subscriptionFromDto.getUserId())).thenReturn(true);
            when(userRepository.existsById(subscriptionFromDto.getSubscriptionId())).thenReturn(true);


            Executable result = () -> subscriptionService.createSubscription(subscriptionDto);

            assertThrows(LoopSubscriptionException.class, result);
        }

    }

    @Test
    @DisplayName("Create a subscription but is already exist. Should be throw exception")
    void createSubscriptionExist_thenFail() {
        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
             MockedStatic<ZonedDateTime> zonedDateTimeMock = mockStatic(ZonedDateTime.class)) {

            uuidMock.when(UUID::randomUUID).thenReturn(testUserUUID);
            UUID userUUID = UUID.randomUUID();
            uuidMock.when(UUID::randomUUID).thenReturn(testSubscriptionUUID);
            UUID subscriptionUUID = UUID.randomUUID();
            zonedDateTimeMock.when(ZonedDateTime::now).thenReturn(testZonedDateTime);

            SubscriptionDto subscriptionDto = new SubscriptionDto();
            subscriptionDto.setUserId(userUUID);
            subscriptionDto.setSubscriptionId(subscriptionUUID);

            Subscription subscriptionFromDto = new Subscription();
            subscriptionFromDto.setUserId(userUUID);
            subscriptionFromDto.setSubscriptionId(subscriptionUUID);
            subscriptionFromDto.setSubDate(ZonedDateTime.now());

            when(userRepository.existsById(subscriptionFromDto.getUserId())).thenReturn(true);
            when(userRepository.existsById(subscriptionFromDto.getSubscriptionId())).thenReturn(true);
            when(subscriptionRepository.existsByUserIdAndSubscriptionId(subscriptionFromDto.getUserId(),
                    subscriptionFromDto.getSubscriptionId())).thenReturn(true)
                    .thenThrow(SubscriptionAlreadyExistException.class);

            Executable result = () -> subscriptionService.createSubscription(subscriptionDto);

            assertThrows(SubscriptionAlreadyExistException.class, result);
        }

    }

    @Test
    @DisplayName("Get all user subscriptions. Should be successful")
    void getAllUserSubscription() {
        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
             MockedStatic<ZonedDateTime> zonedDateTimeMock = mockStatic(ZonedDateTime.class)) {

            uuidMock.when(UUID::randomUUID).thenReturn(testUserUUID);
            UUID mainUserUUID = UUID.randomUUID();
            zonedDateTimeMock.when(ZonedDateTime::now).thenReturn(testZonedDateTime);
            uuidMock.when(UUID::randomUUID).thenReturn(testSubscriptionUUID);
            UUID firstSubUserUUID = UUID.randomUUID();
            uuidMock.when(UUID::randomUUID).thenReturn(testSecondUserUUID);
            UUID secondSubUserUUID = UUID.randomUUID();

            Subscription firstSubscription = new Subscription();
            firstSubscription.setUserId(mainUserUUID);
            firstSubscription.setSubscriptionId(firstSubUserUUID);
            firstSubscription.setSubDate(ZonedDateTime.now());

            Subscription secondSubscription = new Subscription();
            secondSubscription.setUserId(mainUserUUID);
            secondSubscription.setSubscriptionId(secondSubUserUUID);
            secondSubscription.setSubDate(ZonedDateTime.now());

            List<Subscription> resultList = new ArrayList<>();
            resultList.add(firstSubscription);
            resultList.add(secondSubscription);

            SubscriptionDto firstSubscriptionDto = new SubscriptionDto();
            firstSubscriptionDto.setUserId(mainUserUUID);
            firstSubscriptionDto.setSubscriptionId(firstSubUserUUID);
            firstSubscriptionDto.setSubDate(ZonedDateTime.now());

            SubscriptionDto secondSubscriptionDto = new SubscriptionDto();
            secondSubscriptionDto.setUserId(mainUserUUID);
            secondSubscriptionDto.setSubscriptionId(secondSubUserUUID);
            secondSubscriptionDto.setSubDate(ZonedDateTime.now());

            List<SubscriptionDto> resultExpectedDtoList = new ArrayList<>();
            resultExpectedDtoList.add(firstSubscriptionDto);
            resultExpectedDtoList.add(secondSubscriptionDto);

            when(subscriptionRepository.existsSubscriptionsByUserId(mainUserUUID)).thenReturn(true);
            when(subscriptionRepository.findAllSubscriptionsByUserId(mainUserUUID)).thenReturn(resultList);

            List<SubscriptionDto> resultActualDtoList = subscriptionService.getAllUserSubscription(mainUserUUID);

            assertNotNull(resultActualDtoList);
            assertEquals(resultExpectedDtoList, resultActualDtoList);

        }

    }

    @Test
    @DisplayName("Delete user but userId does not exist. Should be throw exception")
    void getAllUserSubscriptionUserIdNotExist_thenFail() {
        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
             MockedStatic<ZonedDateTime> zonedDateTimeMock = mockStatic(ZonedDateTime.class)) {

            uuidMock.when(UUID::randomUUID).thenReturn(testUserUUID);
            UUID mainUserUUID = UUID.randomUUID();
            zonedDateTimeMock.when(ZonedDateTime::now).thenReturn(testZonedDateTime);


            when(subscriptionRepository.existsSubscriptionsByUserId(mainUserUUID)).thenReturn(false)
                    .thenThrow(SubscriptionNotFoundException.class);


            Executable result = () -> subscriptionService.getAllUserSubscription(mainUserUUID);

            assertThrows(SubscriptionNotFoundException.class, result);

        }

    }

    @Test
    @DisplayName("Delete subscription. Should be successful")
    void deleteSubscription() {

        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
             MockedStatic<ZonedDateTime> zonedDateTimeMock = mockStatic(ZonedDateTime.class)) {

            uuidMock.when(UUID::randomUUID).thenReturn(testUserUUID);
            UUID userUUID = UUID.randomUUID();
            uuidMock.when(UUID::randomUUID).thenReturn(testSubscriptionUUID);
            UUID subscriptionUUID = UUID.randomUUID();
            zonedDateTimeMock.when(ZonedDateTime::now).thenReturn(testZonedDateTime);

            SubscriptionDto subscriptionDto = new SubscriptionDto();
            subscriptionDto.setUserId(userUUID);
            subscriptionDto.setSubscriptionId(subscriptionUUID);

            Subscription subscriptionFromDto = new Subscription();
            subscriptionFromDto.setUserId(userUUID);
            subscriptionFromDto.setSubscriptionId(subscriptionUUID);
            subscriptionFromDto.setSubDate(ZonedDateTime.now());

            when(subscriptionRepository.existsByUserIdAndSubscriptionId(subscriptionFromDto.getUserId(), subscriptionFromDto
                    .getSubscriptionId())).thenReturn(true);


            String result = subscriptionService.deleteSubscription(subscriptionDto);

            assertEquals("Subscription - " + subscriptionDto + " successfully deleted", result);

        }
    }

    @Test
    @DisplayName("Delete subscription but User and Subscription id`s does not exist. Should be throw exception")
    void deleteSubscriptionNotExistUserAndSubscriptionId_thenFail() {

        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
             MockedStatic<ZonedDateTime> zonedDateTimeMock = mockStatic(ZonedDateTime.class)) {

            uuidMock.when(UUID::randomUUID).thenReturn(testUserUUID);
            UUID userUUID = UUID.randomUUID();
            uuidMock.when(UUID::randomUUID).thenReturn(testSubscriptionUUID);
            UUID subscriptionUUID = UUID.randomUUID();
            zonedDateTimeMock.when(ZonedDateTime::now).thenReturn(testZonedDateTime);

            SubscriptionDto subscriptionDto = new SubscriptionDto();
            subscriptionDto.setUserId(userUUID);
            subscriptionDto.setSubscriptionId(subscriptionUUID);

            Subscription subscriptionFromDto = new Subscription();
            subscriptionFromDto.setUserId(userUUID);
            subscriptionFromDto.setSubscriptionId(subscriptionUUID);
            subscriptionFromDto.setSubDate(ZonedDateTime.now());

            when(subscriptionRepository.existsByUserIdAndSubscriptionId(subscriptionFromDto.getUserId(), subscriptionFromDto
                    .getSubscriptionId())).thenReturn(false).thenThrow(SubscriptionNotFoundException.class);


            Executable result = () -> subscriptionService.deleteSubscription(subscriptionDto);

            assertThrows(SubscriptionNotFoundException.class, result);

        }
    }
}