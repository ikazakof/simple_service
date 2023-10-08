package com.example.simple_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Schema(description = "Дто для города")
public class SubscriptionDto {

    @Schema(description = "Автоматически сгенерированный ранее идентификатор пользователя"
            , example = "ab93a81a-70e8-49cc-a033-5f679bfa7ebe")
    UUID userId;

    @Schema(description = "Автоматически сгенерированный ранее идентификатор подписки"
            , example = "bc00be57-937e-480c-8d04-0884c866ab99")
    UUID subscriptionId;

    @Schema(description = "Дата подписки", example = "2023-02-26 19:02:29.110138 +00:00")
    private ZonedDateTime subDate;

}
