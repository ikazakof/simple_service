package com.example.simple_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UserDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String password;
    private String phone;
    private ZonedDateTime regDate;
    private ZonedDateTime updateDate;
    private Boolean isDeleted;

}
