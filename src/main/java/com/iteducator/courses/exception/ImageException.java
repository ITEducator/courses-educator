package com.iteducator.courses.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ACCEPTED)
public class ImageException extends RuntimeException {

    public ImageException(String message) {
        super(message);
    }
}
