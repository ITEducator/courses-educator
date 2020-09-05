package com.iteducator.courses.exception.handler;

import com.iteducator.courses.exception.CourseException;
import com.iteducator.courses.exception.ImageException;
import com.iteducator.courses.exception.response.CourseExceptionResponse;
import com.iteducator.courses.exception.response.ImageExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleCourseNotFoundException(CourseException ex, WebRequest request) {
        CourseExceptionResponse projectNotFoundExceptionResponse = new CourseExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(projectNotFoundExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleImageNotFoundException(ImageException ex, WebRequest request) {
        ImageExceptionResponse imageExceptionResponse = new ImageExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(imageExceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
