package com.example.simple_service.repository;

import com.example.simple_service.entity.City;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Table(name = "cities")
public interface CityRepository extends JpaRepository<City, UUID> {

    Boolean existsByTitle(String title);

}
