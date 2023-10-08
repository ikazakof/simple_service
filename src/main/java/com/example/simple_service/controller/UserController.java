package com.example.simple_service.controller;

import com.example.simple_service.dto.SubscriptionDto;
import com.example.simple_service.dto.UserDto;
import com.example.simple_service.utils.web.WebConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "User controller", description = "Работа с пользователями")
@RequestMapping(value = WebConstant.VERSION_URL + "/users")
public interface UserController {

    @PostMapping
    @Operation(summary = "Создание пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Пользователь создан",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404",
                    description = "Параметр не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto);

    @GetMapping(value = "/{id}")
    @Operation(summary = "Получение пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Пользователь получен",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Пользователь не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    ResponseEntity<UserDto> getUserById(@PathVariable UUID id);

    @PutMapping
    @Operation(summary = "Обновление пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Пользователь обновлен",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Параметр не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    ResponseEntity<UserDto> updateUserById(@RequestBody UserDto userDto);

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Удаление пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Пользователь удален",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404",
                    description = "Пользователь не найден",
                    content = @Content(mediaType =  MediaType.APPLICATION_JSON_VALUE))})
    ResponseEntity<String> deleteUserById(@PathVariable UUID id, @RequestParam(required = false, defaultValue = "false") String hardDelete);

}
