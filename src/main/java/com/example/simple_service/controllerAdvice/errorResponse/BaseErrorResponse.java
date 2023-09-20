package com.example.simple_service.controllerAdvice.errorResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseErrorResponse {
    private String errorMessage;
}
