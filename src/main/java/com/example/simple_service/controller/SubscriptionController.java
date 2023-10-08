package com.example.simple_service.controller;

import com.example.simple_service.dto.SubscriptionDto;
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

@Tag(name = "Subscription controller", description = "Работа с подписками пользователя")
@RequestMapping(value = WebConstant.VERSION_URL + "/subscriptions")
public interface SubscriptionController {

    @PostMapping
    @Operation(summary = "Создание подписки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Подписка создана",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SubscriptionDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Некорректный запрос",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404",
                    description = "id не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    ResponseEntity<SubscriptionDto> createSubscription(@RequestBody SubscriptionDto subscriptionDto);

    @GetMapping(value = "/{id}")
    @Operation(summary = "Получение всех подписок пользователя по его id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Подписки получены",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(allOf = {List.class, SubscriptionDto.class}))),
            @ApiResponse(responseCode = "404",
                    description = "Подписки не найдены",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    ResponseEntity<List<SubscriptionDto>> getAllUserSubscriptions(@PathVariable UUID id);

    @DeleteMapping
    @Operation(summary = "Удаление подписки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Подписка удалена",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404",
                    description = "Подписка не найдена",
                    content = @Content(mediaType =  MediaType.APPLICATION_JSON_VALUE))})
    ResponseEntity<String> deleteSubscription(@RequestBody SubscriptionDto subscriptionDto);

}
