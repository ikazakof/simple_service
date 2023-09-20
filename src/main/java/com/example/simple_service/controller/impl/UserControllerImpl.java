package com.example.simple_service.controller.impl;

import com.example.simple_service.controller.UserController;
import com.example.simple_service.dto.UserDto;
import com.example.simple_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDto> getUserById(UUID id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDto> updateUserById(UserDto userDto) {
        return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteUserById(UUID id, String hardDelete) {
        return new ResponseEntity<>(userService.deleteUserById(id, hardDelete), HttpStatus.OK);
    }
}
