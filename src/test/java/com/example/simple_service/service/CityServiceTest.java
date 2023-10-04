package com.example.simple_service.service;

import com.example.simple_service.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@DisplayName("Testing city service functionality.")
public class CityServiceTest {

    private final UUID testUUID = UUID.fromString("4416e9c4-aa48-4e56-b8af-1e57b6fbfae9");

    @Mock
    private CityRepository cityRepository;

    private CityService cityService;

    @BeforeEach
    public void init() {
        cityService = new CityService(cityRepository);
    }

    @Test
    @DisplayName("Check city by title. Should be successful")
    void cityExistByNameTest() {

        String cityTitle = "CityName";

        when(cityRepository.existsByTitle(cityTitle)).thenReturn(true);

        assertEquals(true, cityService.cityExistByName(cityTitle));

    }

    @Test
    @DisplayName("Check city by title. Should be failed")
    void cityNotExistByNameTest() {

        String cityTitle = "CityNameNotExist";

        when(cityRepository.existsByTitle(cityTitle)).thenReturn(false);

        assertEquals(false, cityService.cityExistByName(cityTitle));

    }

}
