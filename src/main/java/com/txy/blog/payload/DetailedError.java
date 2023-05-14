package com.txy.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class DetailedError {
    private Date timestamp;
    private String uri;
    private String message;
}
