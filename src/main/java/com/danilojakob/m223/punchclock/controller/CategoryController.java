package com.danilojakob.m223.punchclock.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {



    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'EMPLOYEE')")
    @GetMapping
    public ResponseEntity getAllCategories() {
        return null;
    }
}
