package com.example.simple_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Schema(description = "Дто для города")
public class CityDto {

    @Schema(description = "Автоматически сгенерированный идентификатор", example = "ab93a81a-70e8-49cc-a033-5f679bfa7ebe")
    private UUID id;

    @Schema(description = "Название города", example = "Arstotska")
    private String title;

}
