package com.danilojakob.m223.punchclock.controller;

import com.danilojakob.m223.punchclock.domain.Category;
import com.danilojakob.m223.punchclock.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'EMPLOYEE')")
    @GetMapping
    public ResponseEntity getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll());
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PutMapping
    public ResponseEntity updateCategory(@Valid @RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategory(category));
    }
}
