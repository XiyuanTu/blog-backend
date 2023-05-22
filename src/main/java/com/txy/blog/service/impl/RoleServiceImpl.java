package com.txy.blog.service.impl;

import com.txy.blog.entity.Role;
import com.txy.blog.repository.RoleRepository;
import com.txy.blog.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> createRole(String name) {
        Optional<Role> opt = roleRepository.findByName(name);

        if (opt.isPresent()) {
            return Optional.empty();
        }

        Role role = new Role();
        role.setName(name);

        Role newRole = roleRepository.save(role);

        return Optional.of(newRole);
    }
}
