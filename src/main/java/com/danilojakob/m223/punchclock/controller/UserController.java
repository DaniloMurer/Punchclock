package com.danilojakob.m223.punchclock.controller;

import com.danilojakob.m223.punchclock.domain.ApplicationUser;
import com.danilojakob.m223.punchclock.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    private BCryptPasswordEncoder encryption;

    public UserController(UserService userService, BCryptPasswordEncoder encryption) {
        this.userService = userService;
        this.encryption = encryption;
    }

    @PostMapping(value = "/signup")
    public ResponseEntity signUp(@Validated @RequestBody ApplicationUser applicationUser) {
        applicationUser.setPassword(encryption.encode(applicationUser.getPassword()));
        userService.saveUser(applicationUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
