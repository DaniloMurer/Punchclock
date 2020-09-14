package com.danilojakob.m223.punchclock.service;

import com.danilojakob.m223.punchclock.domain.Entry;
import com.danilojakob.m223.punchclock.domain.Role;
import com.danilojakob.m223.punchclock.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }
}
