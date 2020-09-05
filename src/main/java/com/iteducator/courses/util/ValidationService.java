package com.iteducator.courses.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.stream.Collectors;

@Component
public class ValidationService {

    public ResponseEntity<?> mapValidationService(BindingResult result) {
        return result.hasErrors()
                ? new ResponseEntity<>(result.getFieldErrors().stream()
                .filter(error -> error.getDefaultMessage() != null)
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)), HttpStatus.BAD_REQUEST)
                : null;
    }
}
