package com.example.simple_service.service;

import com.example.simple_service.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityService {

    private final CityRepository cityRepository;

    public Boolean cityExistByName(String city) {
        return cityRepository.existsByTitle(city);
    }

}
