package com.example.simple_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;
    private String email;
    private String password;
    private String phone;
    private String City;
    @Column (name = "registration_date")
    private ZonedDateTime regDate;
    @Column (name = "update_date")
    private ZonedDateTime updateDate;
    @Column(name = "is_deleted")
    private Boolean isDeleted;

}
