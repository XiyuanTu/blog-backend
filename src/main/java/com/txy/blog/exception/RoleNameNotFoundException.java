package com.txy.blog.exception;

public class RoleNameNotFoundException extends RuntimeException {
    public RoleNameNotFoundException(String roleName) {
        super(String.format("Role name: %s not found", roleName));
    }
}
