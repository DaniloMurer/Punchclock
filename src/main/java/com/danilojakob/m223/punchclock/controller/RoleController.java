package com.danilojakob.m223.punchclock.controller;

import com.danilojakob.m223.punchclock.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity getAllRoles() {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.findAll());
    }
}
