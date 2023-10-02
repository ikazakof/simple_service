package com.example.simple_service.service;

import com.example.simple_service.dto.UserDto;
import com.example.simple_service.entity.User;
import com.example.simple_service.exception.UserEmailExistException;
import com.example.simple_service.exception.UserNotFoundException;
import com.example.simple_service.exception.UserPhoneExistException;
import com.example.simple_service.mapper.UserMapper;
import com.example.simple_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto createUser(UserDto userDto) {
        log.info("UserDto to create - " + userDto);
        User userToCreate = userMapper.convertDtoToUser(userDto);
        if (userRepository.existsByEmail(userToCreate.getEmail())) {
            log.error("User with this email - " + userToCreate.getEmail() + " already exist");
            throw new UserEmailExistException("User with this email - " + userToCreate.getEmail() + " already exist");
        }
        if (userRepository.existsByPhone(userToCreate.getPhone())) {
            log.error("User with this phone - " + userToCreate.getPhone() + " already exist");
            throw new UserPhoneExistException("User with this phone - " + userToCreate.getPhone() + " already exist");
        }
        User savedUser = userRepository.save(userToCreate);
        log.info("User created - " + savedUser);
        return userMapper.convertUserToDto(savedUser);
    }

    public UserDto getUserById(UUID id) {
        if (!userRepository.existsById(id)) {
            log.error("User with id - " + id + " does not exist");
            throw new UserNotFoundException("User with id - " + id + " does not exist");
        }
        User userFromDB = userRepository.findById(id).get();
        log.info("User found - " + userFromDB);
        return userMapper.convertUserToDto(userFromDB);
    }

    public UserDto updateUser(UserDto userDto) {
        UUID userId = userDto.getId();
        if (!userRepository.existsById(userId)) {
            log.error("User with id - " + userId + " does not exist");
            throw new UserNotFoundException("User with id - " + userId + " does not exist");
        }
        User userToUpdate = userMapper.getUserUpdatedByDto(userDto, userRepository.findById(userId).get());
        User updatedUser = userRepository.save(userToUpdate);
        log.info("Updated user - " + updatedUser);
        return userMapper.convertUserToDto(updatedUser);
    }

    public String deleteUserById(UUID id, String hardDelete) {
        if (!userRepository.existsById(id)) {
            log.error("User with id - " + id + " does not exist");
            throw new UserNotFoundException("User with id - " + id + " does not exist");
        }
        User userFromDB = userRepository.findById(id).get();
        if ("true".equals(hardDelete)) {
            userRepository.deleteById(id);
            log.info("User with id - " + id + " hard deleted");
            return "User with id - " + id + " hard deleted";
        }
        userFromDB.setIsDeleted(true);
        userRepository.save(userFromDB);
        log.info("User with id - " + id + " soft deleted");
        return "User with id - " + id + " soft deleted";
    }
}
