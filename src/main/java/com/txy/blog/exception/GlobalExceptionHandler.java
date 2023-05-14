package com.txy.blog.exception;

import com.txy.blog.payload.DetailedError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DetailedError> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException, WebRequest webRequest) {
        DetailedError detailedError = new DetailedError(new Date(), webRequest.getDescription(false), resourceNotFoundException.getMessage());
        return new ResponseEntity<>(detailedError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<DetailedError> handleBlogAPIException(BlogAPIException blogAPIException, WebRequest webRequest) {
        DetailedError detailedError = new DetailedError(new Date(), webRequest.getDescription(false), blogAPIException.getMessage());
        return new ResponseEntity<>(detailedError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DetailedError> MethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException, WebRequest webRequest) {
        Map<String, String> errors = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });
        DetailedError detailedError = new DetailedError(new Date(), webRequest.getDescription(false), errors.toString());
        return new ResponseEntity<>(detailedError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DetailedError> handleResourceNotFoundException(Exception exception, WebRequest webRequest) {
        DetailedError detailedError = new DetailedError(new Date(), webRequest.getDescription(false), exception.getMessage());
        return new ResponseEntity<>(detailedError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
