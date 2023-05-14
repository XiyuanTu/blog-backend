package com.txy.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BlogAPIException extends RuntimeException {
    public BlogAPIException(String message) {
        super(message);
    }
}
