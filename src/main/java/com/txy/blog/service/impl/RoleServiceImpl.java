package com.txy.blog.service.impl;

import com.txy.blog.entity.Role;
import com.txy.blog.repository.RoleRepository;
import com.txy.blog.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role createRole(String name) {
        Role role = new Role();
        role.setName(name);

        Role newRole = roleRepository.save(role);
        return newRole;
    }
}
