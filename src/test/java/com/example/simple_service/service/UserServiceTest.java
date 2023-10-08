package com.example.simple_service.service;

import com.example.simple_service.dto.UserDto;
import com.example.simple_service.entity.User;
import com.example.simple_service.exception.CityNotFoundException;
import com.example.simple_service.exception.UserEmailExistException;
import com.example.simple_service.exception.UserNotFoundException;
import com.example.simple_service.exception.UserPhoneExistException;
import com.example.simple_service.mapper.UserMapper;
import com.example.simple_service.mapper.UserMapperImpl;
import com.example.simple_service.repository.CityRepository;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

/**
 * Тестирование функционала {@link UserService}.
 */
@ExtendWith({MockitoExtension.class})
@DisplayName("Testing user service functionality.")
public class UserServiceTest {

    private final UUID testUUID = UUID.fromString("4416e9c4-aa48-4e56-b8af-1e57b6fbfae9");
    private final ZonedDateTime testZonedDateTime = ZonedDateTime.parse("2023-09-18T09:34:56+00:00");

    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Mock
    private CityRepository cityRepository;

    private CityService cityService;
    private UserMapper userMapper;

    @BeforeEach
    public void init() {
        userMapper = new UserMapperImpl();
        cityService = new CityService(cityRepository);
        userService = new UserService(userRepository, userMapper, cityService);
    }

    @Test
    @DisplayName("Create a user. Should be successful")
    void createUserTest() {

        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
             MockedStatic<ZonedDateTime> zonedDateTimeMock = mockStatic(ZonedDateTime.class)) {

            UserDto userDto = new UserDto();
            userDto.setEmail("some@mail.ru");
            userDto.setPhone("+79169201203");
            userDto.setFirstName("Igor");
            userDto.setLastName("Barmaleev");
            userDto.setMiddleName("Sergeevich");
            userDto.setPassword("12345asd");
            userDto.setCity("Москва");

            uuidMock.when(UUID::randomUUID).thenReturn(testUUID);
            zonedDateTimeMock.when(ZonedDateTime::now).thenReturn(testZonedDateTime);

            User userFromDto = new User();
            userFromDto.setEmail("some@mail.ru");
            userFromDto.setPhone("+79169201203");
            userFromDto.setCity("Москва");
            userFromDto.setFirstName("Igor");
            userFromDto.setLastName("Barmaleev");
            userFromDto.setMiddleName("Sergeevich");
            userFromDto.setPassword("12345asd");
            userFromDto.setRegDate(ZonedDateTime.now());
            userFromDto.setUpdateDate(ZonedDateTime.now());
            userFromDto.setIsDeleted(false);

            User savedUser = new User();
            savedUser.setId(UUID.randomUUID());
            savedUser.setIsDeleted(false);
            savedUser.setEmail("some@mail.ru");
            savedUser.setPhone("+79169201203");
            savedUser.setCity("Москва");
            savedUser.setFirstName("Igor");
            savedUser.setLastName("Barmaleev");
            savedUser.setMiddleName("Sergeevich");
            savedUser.setRegDate(ZonedDateTime.now());
            savedUser.setUpdateDate(ZonedDateTime.now());
            savedUser.setPassword("12345asd");

            UserDto resultUserDto = new UserDto();
            resultUserDto.setId(UUID.randomUUID());
            resultUserDto.setIsDeleted(false);
            resultUserDto.setEmail("some@mail.ru");
            resultUserDto.setPhone("+79169201203");
            resultUserDto.setCity("Москва");
            resultUserDto.setFirstName("Igor");
            resultUserDto.setLastName("Barmaleev");
            resultUserDto.setMiddleName("Sergeevich");
            resultUserDto.setRegDate(ZonedDateTime.now());
            resultUserDto.setUpdateDate(ZonedDateTime.now());
            resultUserDto.setPassword("12345asd");

            when(cityRepository.existsByTitle(userFromDto.getCity())).thenReturn(true);
            when(userRepository.existsByEmail(userFromDto.getEmail())).thenReturn(false);
            when(userRepository.existsByPhone(userFromDto.getPhone())).thenReturn(false);
            when(userRepository.save(userFromDto)).thenReturn(savedUser);

            UserDto resultUserDtoFromService = userService.createUser(userDto);

            assertNotNull(resultUserDtoFromService);
            assertEquals(resultUserDto.getId(), resultUserDtoFromService.getId());
            assertEquals(resultUserDto.getIsDeleted(), resultUserDtoFromService.getIsDeleted());
            assertEquals(resultUserDto.getEmail(), resultUserDtoFromService.getEmail());
            assertEquals(resultUserDto.getPhone(), resultUserDtoFromService.getPhone());
            assertEquals(resultUserDto.getCity(), resultUserDtoFromService.getCity());
            assertEquals(resultUserDto.getFirstName(), resultUserDtoFromService.getFirstName());
            assertEquals(resultUserDto.getLastName(), resultUserDtoFromService.getLastName());
            assertEquals(resultUserDto.getMiddleName(), resultUserDtoFromService.getMiddleName());
            assertEquals(resultUserDto.getRegDate(), resultUserDtoFromService.getRegDate());
            assertEquals(resultUserDto.getUpdateDate(), resultUserDtoFromService.getUpdateDate());
            assertEquals(resultUserDto.getPassword(), resultUserDtoFromService.getPassword());

        }
    }

    @Test
    @DisplayName("Create a user but city is empty. Should be successful")
    void createUserCityIsEmpty_thenSuccess() {

        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
             MockedStatic<ZonedDateTime> zonedDateTimeMock = mockStatic(ZonedDateTime.class)) {

            UserDto userDto = new UserDto();
            userDto.setEmail("some@mail.ru");
            userDto.setPhone("+79169201203");
            userDto.setFirstName("Igor");
            userDto.setLastName("Barmaleev");
            userDto.setMiddleName("Sergeevich");
            userDto.setPassword("12345asd");
            userDto.setCity("");

            uuidMock.when(UUID::randomUUID).thenReturn(testUUID);
            zonedDateTimeMock.when(ZonedDateTime::now).thenReturn(testZonedDateTime);

            User userFromDto = new User();
            userFromDto.setEmail("some@mail.ru");
            userFromDto.setPhone("+79169201203");
            userFromDto.setCity("");
            userFromDto.setFirstName("Igor");
            userFromDto.setLastName("Barmaleev");
            userFromDto.setMiddleName("Sergeevich");
            userFromDto.setPassword("12345asd");
            userFromDto.setRegDate(ZonedDateTime.now());
            userFromDto.setUpdateDate(ZonedDateTime.now());
            userFromDto.setIsDeleted(false);

            User savedUser = new User();
            savedUser.setId(UUID.randomUUID());
            savedUser.setIsDeleted(false);
            savedUser.setEmail("some@mail.ru");
            savedUser.setPhone("+79169201203");
            savedUser.setCity("");
            savedUser.setFirstName("Igor");
            savedUser.setLastName("Barmaleev");
            savedUser.setMiddleName("Sergeevich");
            savedUser.setRegDate(ZonedDateTime.now());
            savedUser.setUpdateDate(ZonedDateTime.now());
            savedUser.setPassword("12345asd");

            UserDto resultUserDto = new UserDto();
            resultUserDto.setId(UUID.randomUUID());
            resultUserDto.setIsDeleted(false);
            resultUserDto.setEmail("some@mail.ru");
            resultUserDto.setPhone("+79169201203");
            resultUserDto.setCity("");
            resultUserDto.setFirstName("Igor");
            resultUserDto.setLastName("Barmaleev");
            resultUserDto.setMiddleName("Sergeevich");
            resultUserDto.setRegDate(ZonedDateTime.now());
            resultUserDto.setUpdateDate(ZonedDateTime.now());
            resultUserDto.setPassword("12345asd");

            when(userRepository.existsByEmail(userFromDto.getEmail())).thenReturn(false);
            when(userRepository.existsByPhone(userFromDto.getPhone())).thenReturn(false);
            when(userRepository.save(userFromDto)).thenReturn(savedUser);

            UserDto resultUserDtoFromService = userService.createUser(userDto);

            assertNotNull(resultUserDtoFromService);
            assertEquals(resultUserDto.getId(), resultUserDtoFromService.getId());
            assertEquals(resultUserDto.getIsDeleted(), resultUserDtoFromService.getIsDeleted());
            assertEquals(resultUserDto.getEmail(), resultUserDtoFromService.getEmail());
            assertEquals(resultUserDto.getPhone(), resultUserDtoFromService.getPhone());
            assertEquals(resultUserDto.getCity(), resultUserDtoFromService.getCity());
            assertEquals(resultUserDto.getFirstName(), resultUserDtoFromService.getFirstName());
            assertEquals(resultUserDto.getLastName(), resultUserDtoFromService.getLastName());
            assertEquals(resultUserDto.getMiddleName(), resultUserDtoFromService.getMiddleName());
            assertEquals(resultUserDto.getRegDate(), resultUserDtoFromService.getRegDate());
            assertEquals(resultUserDto.getUpdateDate(), resultUserDtoFromService.getUpdateDate());
            assertEquals(resultUserDto.getPassword(), resultUserDtoFromService.getPassword());

        }
    }

    @Test
    @DisplayName("User city does not exist. Should be throw exception")
    void createUserCityNotExist_thenFail() {

        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
             MockedStatic<ZonedDateTime> zonedDateTimeMock = mockStatic(ZonedDateTime.class)) {

            UserDto userDto = new UserDto();
            userDto.setEmail("some@mail.ru");
            userDto.setPhone("+79169201203");
            userDto.setFirstName("Igor");
            userDto.setLastName("Barmaleev");
            userDto.setMiddleName("Sergeevich");
            userDto.setPassword("12345asd");
            userDto.setCity("Москва");

            uuidMock.when(UUID::randomUUID).thenReturn(testUUID);
            zonedDateTimeMock.when(ZonedDateTime::now).thenReturn(testZonedDateTime);

            User userFromDto = new User();
            userFromDto.setEmail("some@mail.ru");
            userFromDto.setPhone("+79169201203");
            userFromDto.setCity("Москва");
            userFromDto.setFirstName("Igor");
            userFromDto.setLastName("Barmaleev");
            userFromDto.setMiddleName("Sergeevich");
            userFromDto.setPassword("12345asd");
            userFromDto.setRegDate(ZonedDateTime.now());
            userFromDto.setUpdateDate(ZonedDateTime.now());
            userFromDto.setIsDeleted(false);

            when(cityRepository.existsByTitle(userFromDto.getCity())).thenReturn(false).thenThrow(CityNotFoundException.class);

            Executable result = () -> userService.createUser(userDto);

            assertThrows(CityNotFoundException.class, result);
        }
    }

    @Test
    @DisplayName("User email already exist. Should be throw exception")
    void createUserEmailExist_thenFail() {

        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
             MockedStatic<ZonedDateTime> zonedDateTimeMock = mockStatic(ZonedDateTime.class)) {

            UserDto userDto = new UserDto();
            userDto.setEmail("some@mail.ru");
            userDto.setPhone("+79169201203");
            userDto.setFirstName("Igor");
            userDto.setLastName("Barmaleev");
            userDto.setMiddleName("Sergeevich");
            userDto.setPassword("12345asd");
            userDto.setCity("Москва");

            uuidMock.when(UUID::randomUUID).thenReturn(testUUID);
            zonedDateTimeMock.when(ZonedDateTime::now).thenReturn(testZonedDateTime);

            User userFromDto = new User();
            userFromDto.setEmail("some@mail.ru");
            userFromDto.setPhone("+79169201203");
            userFromDto.setCity("Москва");
            userFromDto.setFirstName("Igor");
            userFromDto.setLastName("Barmaleev");
            userFromDto.setMiddleName("Sergeevich");
            userFromDto.setPassword("12345asd");
            userFromDto.setRegDate(ZonedDateTime.now());
            userFromDto.setUpdateDate(ZonedDateTime.now());
            userFromDto.setIsDeleted(false);

            when(cityRepository.existsByTitle(userFromDto.getCity())).thenReturn(true);
            when(userRepository.existsByEmail(userFromDto.getEmail())).thenReturn(true).thenThrow(UserEmailExistException.class);

            Executable result = () -> userService.createUser(userDto);

            assertThrows(UserEmailExistException.class, result);
        }
    }

    @Test
    @DisplayName("User phone already exist. Should be throw exception")
    void createUserPhoneExist_thenFail() {

        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
             MockedStatic<ZonedDateTime> zonedDateTimeMock = mockStatic(ZonedDateTime.class)) {

            UserDto userDto = new UserDto();
            userDto.setEmail("some@mail.ru");
            userDto.setPhone("+79169201203");
            userDto.setFirstName("Igor");
            userDto.setLastName("Barmaleev");
            userDto.setMiddleName("Sergeevich");
            userDto.setPassword("12345asd");

            uuidMock.when(UUID::randomUUID).thenReturn(testUUID);
            zonedDateTimeMock.when(ZonedDateTime::now).thenReturn(testZonedDateTime);

            User userFromDto = new User();
            userFromDto.setEmail("some@mail.ru");
            userFromDto.setPhone("+79169201203");
            userFromDto.setFirstName("Igor");
            userFromDto.setLastName("Barmaleev");
            userFromDto.setMiddleName("Sergeevich");
            userFromDto.setPassword("12345asd");
            userFromDto.setRegDate(ZonedDateTime.now());
            userFromDto.setUpdateDate(ZonedDateTime.now());
            userFromDto.setIsDeleted(false);

            when(cityRepository.existsByTitle(userFromDto.getCity())).thenReturn(true);
            when(userRepository.existsByPhone(userFromDto.getPhone())).thenReturn(true).thenThrow(UserPhoneExistException.class);

            Executable result = () -> userService.createUser(userDto);

            assertThrows(UserPhoneExistException.class, result);
        }
    }

    @Test
    @DisplayName("Get user by ID. Should be successful")
    void getUserByIdTest() {

        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
             MockedStatic<ZonedDateTime> zonedDateTimeMock = mockStatic(ZonedDateTime.class)) {

            uuidMock.when(UUID::randomUUID).thenReturn(testUUID);
            zonedDateTimeMock.when(ZonedDateTime::now).thenReturn(testZonedDateTime);

            UUID userUUID = UUID.randomUUID();

            User findedUser = new User();
            findedUser.setId(UUID.randomUUID());
            findedUser.setIsDeleted(false);
            findedUser.setEmail("some@mail.ru");
            findedUser.setPhone("+79169201203");
            findedUser.setCity("Москва");
            findedUser.setFirstName("Igor");
            findedUser.setLastName("Barmaleev");
            findedUser.setMiddleName("Sergeevich");
            findedUser.setRegDate(ZonedDateTime.now());
            findedUser.setUpdateDate(ZonedDateTime.now());
            findedUser.setPassword("12345asd");

            UserDto findedUserDto = new UserDto();
            findedUserDto.setId(UUID.randomUUID());
            findedUserDto.setIsDeleted(false);
            findedUserDto.setEmail("some@mail.ru");
            findedUserDto.setPhone("+79169201203");
            findedUserDto.setCity("Москва");
            findedUserDto.setFirstName("Igor");
            findedUserDto.setLastName("Barmaleev");
            findedUserDto.setMiddleName("Sergeevich");
            findedUserDto.setRegDate(ZonedDateTime.now());
            findedUserDto.setUpdateDate(ZonedDateTime.now());
            findedUserDto.setPassword("12345asd");

            when(userRepository.existsById(userUUID)).thenReturn(true);
            when(userRepository.findById(userUUID)).thenReturn(Optional.of(findedUser));

            UserDto resultUserDtoFromService = userService.getUserById(userUUID);
            assertNotNull(resultUserDtoFromService);
            assertEquals(findedUserDto.getId(), resultUserDtoFromService.getId());
            assertEquals(findedUserDto.getIsDeleted(), resultUserDtoFromService.getIsDeleted());
            assertEquals(findedUserDto.getEmail(), resultUserDtoFromService.getEmail());
            assertEquals(findedUserDto.getPhone(), resultUserDtoFromService.getPhone());
            assertEquals(findedUserDto.getCity(), resultUserDtoFromService.getCity());
            assertEquals(findedUserDto.getFirstName(), resultUserDtoFromService.getFirstName());
            assertEquals(findedUserDto.getLastName(), resultUserDtoFromService.getLastName());
            assertEquals(findedUserDto.getMiddleName(), resultUserDtoFromService.getMiddleName());
            assertEquals(findedUserDto.getRegDate(), resultUserDtoFromService.getRegDate());
            assertEquals(findedUserDto.getUpdateDate(), resultUserDtoFromService.getUpdateDate());
            assertEquals(findedUserDto.getPassword(), resultUserDtoFromService.getPassword());

        }
    }

    @Test
    @DisplayName("Get user by not exists ID. Should be throw exception")
    void getUserById_thenFail() {

        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
             MockedStatic<ZonedDateTime> zonedDateTimeMock = mockStatic(ZonedDateTime.class)) {

            uuidMock.when(UUID::randomUUID).thenReturn(testUUID);
            zonedDateTimeMock.when(ZonedDateTime::now).thenReturn(testZonedDateTime);

            UUID userUUID = UUID.randomUUID();

            when(userRepository.existsById(userUUID)).thenReturn(false).thenThrow(UserNotFoundException.class);

            Executable result = () -> userService.getUserById(userUUID);

            assertThrows(UserNotFoundException.class, result);
        }
    }

    @Test
    @DisplayName("Update a user. Should be successful")
    void updateUserTest() {

        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
             MockedStatic<ZonedDateTime> zonedDateTimeMock = mockStatic(ZonedDateTime.class)) {

            uuidMock.when(UUID::randomUUID).thenReturn(testUUID);
            zonedDateTimeMock.when(ZonedDateTime::now).thenReturn(testZonedDateTime);

            UserDto userDto = new UserDto();
            userDto.setId(UUID.randomUUID());
            userDto.setEmail("update@mail.ru");
            userDto.setPhone("+79169201203");
            userDto.setCity("Москва");
            userDto.setFirstName("Igor");
            userDto.setLastName("Dekard");
            userDto.setMiddleName("Sergeevich");
            userDto.setPassword("12345asd");

            User userToUpdate = new User();
            userToUpdate.setId(UUID.randomUUID());
            userToUpdate.setEmail("some@mail.ru");
            userToUpdate.setPhone("+79169201203");
            userToUpdate.setCity("Москва");
            userToUpdate.setFirstName("Igor");
            userToUpdate.setLastName("Barmaleev");
            userToUpdate.setMiddleName("Sergeevich");
            userToUpdate.setPassword("12345asd");
            userToUpdate.setRegDate(ZonedDateTime.now());
            userToUpdate.setUpdateDate(ZonedDateTime.now());
            userToUpdate.setIsDeleted(false);

            User updatedUser = new User();
            updatedUser.setId(UUID.randomUUID());
            updatedUser.setEmail("update@mail.ru");
            updatedUser.setPhone("+79169201203");
            updatedUser.setCity("Москва");
            updatedUser.setFirstName("Igor");
            updatedUser.setLastName("Dekard");
            updatedUser.setMiddleName("Sergeevich");
            updatedUser.setPassword("12345asd");
            updatedUser.setRegDate(ZonedDateTime.now());
            updatedUser.setUpdateDate(ZonedDateTime.now());
            updatedUser.setIsDeleted(false);

            UserDto resultUserDto = new UserDto();
            resultUserDto.setId(UUID.randomUUID());
            resultUserDto.setIsDeleted(false);
            resultUserDto.setEmail("update@mail.ru");
            resultUserDto.setPhone("+79169201203");
            resultUserDto.setCity("Москва");
            resultUserDto.setFirstName("Igor");
            resultUserDto.setLastName("Dekard");
            resultUserDto.setMiddleName("Sergeevich");
            resultUserDto.setRegDate(ZonedDateTime.now());
            resultUserDto.setUpdateDate(ZonedDateTime.now());
            resultUserDto.setPassword("12345asd");

            when(userRepository.existsById(userDto.getId())).thenReturn(true);
            when(userRepository.findById(userDto.getId())).thenReturn(Optional.of(userToUpdate));
            when(cityRepository.existsByTitle(userDto.getCity())).thenReturn(true);
            when(userRepository.save(updatedUser)).thenReturn(updatedUser);

            UserDto resultUserDtoFromService = userService.updateUser(userDto);

            assertNotNull(resultUserDtoFromService);
            assertEquals(resultUserDto.getId(), resultUserDtoFromService.getId());
            assertEquals(resultUserDto.getIsDeleted(), resultUserDtoFromService.getIsDeleted());
            assertEquals(resultUserDto.getEmail(), resultUserDtoFromService.getEmail());
            assertEquals(resultUserDto.getPhone(), resultUserDtoFromService.getPhone());
            assertEquals(resultUserDto.getCity(), resultUserDtoFromService.getCity());
            assertEquals(resultUserDto.getFirstName(), resultUserDtoFromService.getFirstName());
            assertEquals(resultUserDto.getLastName(), resultUserDtoFromService.getLastName());
            assertEquals(resultUserDto.getMiddleName(), resultUserDtoFromService.getMiddleName());
            assertEquals(resultUserDto.getRegDate(), resultUserDtoFromService.getRegDate());
            assertEquals(resultUserDto.getUpdateDate(), resultUserDtoFromService.getUpdateDate());
            assertEquals(resultUserDto.getPassword(), resultUserDtoFromService.getPassword());

        }
    }

    @Test
    @DisplayName("Update a user but city is empty. Should be successful")
    void updateUserCityEmpty_thenSuccess() {

        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
             MockedStatic<ZonedDateTime> zonedDateTimeMock = mockStatic(ZonedDateTime.class)) {

            uuidMock.when(UUID::randomUUID).thenReturn(testUUID);
            zonedDateTimeMock.when(ZonedDateTime::now).thenReturn(testZonedDateTime);

            UserDto userDto = new UserDto();
            userDto.setId(UUID.randomUUID());
            userDto.setEmail("update@mail.ru");
            userDto.setPhone("+79169201203");
            userDto.setCity("");
            userDto.setFirstName("Igor");
            userDto.setLastName("Dekard");
            userDto.setMiddleName("Sergeevich");
            userDto.setPassword("12345asd");

            User userToUpdate = new User();
            userToUpdate.setId(UUID.randomUUID());
            userToUpdate.setEmail("some@mail.ru");
            userToUpdate.setPhone("+79169201203");
            userToUpdate.setCity("");
            userToUpdate.setFirstName("Igor");
            userToUpdate.setLastName("Barmaleev");
            userToUpdate.setMiddleName("Sergeevich");
            userToUpdate.setPassword("12345asd");
            userToUpdate.setRegDate(ZonedDateTime.now());
            userToUpdate.setUpdateDate(ZonedDateTime.now());
            userToUpdate.setIsDeleted(false);

            User updatedUser = new User();
            updatedUser.setId(UUID.randomUUID());
            updatedUser.setEmail("update@mail.ru");
            updatedUser.setPhone("+79169201203");
            updatedUser.setCity("");
            updatedUser.setFirstName("Igor");
            updatedUser.setLastName("Dekard");
            updatedUser.setMiddleName("Sergeevich");
            updatedUser.setPassword("12345asd");
            updatedUser.setRegDate(ZonedDateTime.now());
            updatedUser.setUpdateDate(ZonedDateTime.now());
            updatedUser.setIsDeleted(false);

            UserDto resultUserDto = new UserDto();
            resultUserDto.setId(UUID.randomUUID());
            resultUserDto.setIsDeleted(false);
            resultUserDto.setEmail("update@mail.ru");
            resultUserDto.setPhone("+79169201203");
            resultUserDto.setCity("");
            resultUserDto.setFirstName("Igor");
            resultUserDto.setLastName("Dekard");
            resultUserDto.setMiddleName("Sergeevich");
            resultUserDto.setRegDate(ZonedDateTime.now());
            resultUserDto.setUpdateDate(ZonedDateTime.now());
            resultUserDto.setPassword("12345asd");

            when(userRepository.existsById(userDto.getId())).thenReturn(true);
            when(userRepository.findById(userDto.getId())).thenReturn(Optional.of(userToUpdate));
            when(userRepository.save(updatedUser)).thenReturn(updatedUser);

            UserDto resultUserDtoFromService = userService.updateUser(userDto);

            assertNotNull(resultUserDtoFromService);
            assertEquals(resultUserDto.getId(), resultUserDtoFromService.getId());
            assertEquals(resultUserDto.getIsDeleted(), resultUserDtoFromService.getIsDeleted());
            assertEquals(resultUserDto.getEmail(), resultUserDtoFromService.getEmail());
            assertEquals(resultUserDto.getPhone(), resultUserDtoFromService.getPhone());
            assertEquals(resultUserDto.getCity(), resultUserDtoFromService.getCity());
            assertEquals(resultUserDto.getFirstName(), resultUserDtoFromService.getFirstName());
            assertEquals(resultUserDto.getLastName(), resultUserDtoFromService.getLastName());
            assertEquals(resultUserDto.getMiddleName(), resultUserDtoFromService.getMiddleName());
            assertEquals(resultUserDto.getRegDate(), resultUserDtoFromService.getRegDate());
            assertEquals(resultUserDto.getUpdateDate(), resultUserDtoFromService.getUpdateDate());
            assertEquals(resultUserDto.getPassword(), resultUserDtoFromService.getPassword());

        }
    }

    @Test
    @DisplayName("Update a user but city does not exist. Should be throw exception")
    void updateUserCityNotExist_thenFail() {

        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
             MockedStatic<ZonedDateTime> zonedDateTimeMock = mockStatic(ZonedDateTime.class)) {

            uuidMock.when(UUID::randomUUID).thenReturn(testUUID);
            zonedDateTimeMock.when(ZonedDateTime::now).thenReturn(testZonedDateTime);

            UserDto userDto = new UserDto();
            userDto.setId(UUID.randomUUID());
            userDto.setEmail("update@mail.ru");
            userDto.setPhone("+79169201203");
            userDto.setCity("Москва");
            userDto.setFirstName("Igor");
            userDto.setLastName("Dekard");
            userDto.setMiddleName("Sergeevich");
            userDto.setPassword("12345asd");

            User userToUpdate = new User();
            userToUpdate.setId(UUID.randomUUID());
            userToUpdate.setEmail("some@mail.ru");
            userToUpdate.setPhone("+79169201203");
            userToUpdate.setCity("Москва");
            userToUpdate.setFirstName("Igor");
            userToUpdate.setLastName("Barmaleev");
            userToUpdate.setMiddleName("Sergeevich");
            userToUpdate.setPassword("12345asd");
            userToUpdate.setRegDate(ZonedDateTime.now());
            userToUpdate.setUpdateDate(ZonedDateTime.now());
            userToUpdate.setIsDeleted(false);

            when(userRepository.existsById(userDto.getId())).thenReturn(true);
            when(userRepository.findById(userDto.getId())).thenReturn(Optional.of(userToUpdate));
            when(cityRepository.existsByTitle(userToUpdate.getCity())).thenReturn(false).thenThrow(CityNotFoundException.class);

            Executable result = () -> userService.updateUser(userDto);

            assertThrows(CityNotFoundException.class, result);


        }
    }

    @Test
    @DisplayName("Delete user soft. Should be successful")
    void deleteUserSoftTest() {
        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class)) {
            uuidMock.when(UUID::randomUUID).thenReturn(testUUID);

            UUID userUUID = UUID.randomUUID();
            String hardDelete = "false";

            User findedUser = new User();
            findedUser.setId(UUID.randomUUID());
            findedUser.setIsDeleted(false);
            findedUser.setEmail("some@mail.ru");
            findedUser.setPhone("+79169201203");
            findedUser.setFirstName("Igor");
            findedUser.setLastName("Barmaleev");
            findedUser.setMiddleName("Sergeevich");
            findedUser.setRegDate(ZonedDateTime.now());
            findedUser.setUpdateDate(ZonedDateTime.now());
            findedUser.setPassword("12345asd");

            when(userRepository.existsById(userUUID)).thenReturn(true);
            when(userRepository.findById(userUUID)).thenReturn(Optional.of(findedUser));

            String result = userService.deleteUserById(userUUID, hardDelete);

            assertEquals("User with id - " + userUUID + " soft deleted", result);

        }
    }

    @Test
    @DisplayName("Delete user hard. Should be successful")
    void deleteUserHardTest() {
        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class)) {
            uuidMock.when(UUID::randomUUID).thenReturn(testUUID);

            UUID userUUID = UUID.randomUUID();
            String hardDelete = "true";

            User findedUser = new User();
            findedUser.setId(UUID.randomUUID());
            findedUser.setIsDeleted(false);
            findedUser.setEmail("some@mail.ru");
            findedUser.setPhone("+79169201203");
            findedUser.setFirstName("Igor");
            findedUser.setLastName("Barmaleev");
            findedUser.setMiddleName("Sergeevich");
            findedUser.setRegDate(ZonedDateTime.now());
            findedUser.setUpdateDate(ZonedDateTime.now());
            findedUser.setPassword("12345asd");

            when(userRepository.existsById(userUUID)).thenReturn(true);
            when(userRepository.findById(userUUID)).thenReturn(Optional.of(findedUser));

            String result = userService.deleteUserById(userUUID, hardDelete);

            assertEquals("User with id - " + userUUID + " hard deleted", result);

        }
    }

    @Test
    @DisplayName("Delete user but userId does not exist. Should be throw exception")
    void deleteUserByIdUserIdNotExist_thenFail() {
        try (MockedStatic<UUID> uuidMock = mockStatic(UUID.class)) {
            uuidMock.when(UUID::randomUUID).thenReturn(testUUID);

            UUID userUUID = UUID.randomUUID();
            String hardDelete = "false";

            when(userRepository.existsById(userUUID)).thenReturn(false).thenThrow(UserNotFoundException.class);

            Executable result = () -> userService.deleteUserById(userUUID, hardDelete);

            assertThrows(UserNotFoundException.class, result);

        }
    }

}
