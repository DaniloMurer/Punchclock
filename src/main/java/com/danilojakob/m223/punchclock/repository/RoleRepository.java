package com.danilojakob.m223.punchclock.repository;

import com.danilojakob.m223.punchclock.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
