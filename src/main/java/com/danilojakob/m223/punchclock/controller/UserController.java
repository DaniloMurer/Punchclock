package com.danilojakob.m223.punchclock.controller;

import com.danilojakob.m223.punchclock.domain.ApplicationUser;
import com.danilojakob.m223.punchclock.domain.Role;
import com.danilojakob.m223.punchclock.service.CategoryService;
import com.danilojakob.m223.punchclock.service.RoleService;
import com.danilojakob.m223.punchclock.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private RoleService roleService;
    private BCryptPasswordEncoder encryption;

    public UserController(UserService userService, BCryptPasswordEncoder encryption, RoleService roleService) {
        this.userService = userService;
        this.encryption = encryption;
        this.roleService = roleService;
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PostMapping(value = "/signup")
    public ResponseEntity signUp(@Validated @RequestBody ApplicationUser applicationUser) {
        applicationUser.setPassword(encryption.encode(applicationUser.getPassword()));
        Role role = applicationUser.getRoles().stream().findFirst().get();
        Role newRole = roleService.findByName(role.getName());
        userService.saveUser(applicationUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
