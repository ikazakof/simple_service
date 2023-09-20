package com.example.simple_service.repository;

import com.example.simple_service.entity.User;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Table(name = "users")
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String userEmail);

    boolean existsByPhone(String userPhone);

}
