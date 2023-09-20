package com.example.simple_service.controller;

import com.example.simple_service.dto.UserDto;
import com.example.simple_service.utils.WebConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(value = WebConstant.VERSION_URL + "/users")
public interface UserController {

    @PostMapping
    ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto);

    @GetMapping(value = "/{id}")
    ResponseEntity<UserDto> getUserById(@PathVariable UUID id);

    @PutMapping
    ResponseEntity<UserDto> updateUserById(@RequestBody UserDto userDto);

    @DeleteMapping(value = "/{id}")
    ResponseEntity<String> deleteUserById(@PathVariable UUID id, @RequestParam(required = false, defaultValue = "false") String hardDelete);

}
