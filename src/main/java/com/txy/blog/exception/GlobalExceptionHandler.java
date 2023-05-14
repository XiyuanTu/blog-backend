package com.txy.blog.exception;

import com.txy.blog.payload.DetailedError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DetailedError> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException, WebRequest webRequest) {
        DetailedError detailedError = new DetailedError(new Date(), webRequest.getDescription(false), resourceNotFoundException.getMessage());
        return new ResponseEntity<>(detailedError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<DetailedError> handleResourceNotFoundException(BlogAPIException blogAPIException, WebRequest webRequest) {
        DetailedError detailedError = new DetailedError(new Date(), webRequest.getDescription(false), blogAPIException.getMessage());
        return new ResponseEntity<>(detailedError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DetailedError> handleResourceNotFoundException(Exception exception, WebRequest webRequest) {
        DetailedError detailedError = new DetailedError(new Date(), webRequest.getDescription(false), exception.getMessage());
        return new ResponseEntity<>(detailedError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
