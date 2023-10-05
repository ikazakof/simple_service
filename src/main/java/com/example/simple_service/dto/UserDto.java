package com.example.simple_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UserDto {

    @Schema(description = "Автоматически сгенерированный идентификатор", example = "ab93a81a-70e8-49cc-a033-5f679bfa7ebe")
    private UUID id;

    @Schema(description = "Имя", example = "Foma")
    private String firstName;

    @Schema(description = "Фамилия", example = "Kinyaev")
    private String lastName;

    @Schema(description = "Отчество", example = "Borisovich")
    private String middleName;

    @Schema(description = "Почта", example = "example@mail.ru")
    private String email;

    @Schema(description = "Пароль", example = "somePass231")
    private String password;

    @Schema(description = "Телефон", example = "+79144256356")
    private String phone;

    @Schema(description = "Город", example = "Arstotska")
    private String city;

    @Schema(description = "Дата регистрации", example = "2023-10-01 19:02:29.110138 +00:00")
    private ZonedDateTime regDate;

    @Schema(description = "Дата обновления", example = "2023-10-02 19:02:29.110138 +00:00")
    private ZonedDateTime updateDate;

    @Schema(description = "Объект удален?", example = "false")
    private Boolean isDeleted;

}
