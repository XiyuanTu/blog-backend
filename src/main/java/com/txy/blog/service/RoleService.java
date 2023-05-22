package com.txy.blog.service;

import com.txy.blog.entity.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> createRole(String name);
}
