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

    /**
     * Get all Categories from the database
     * @return {@link ResponseEntity} with Status Code and data
     */
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'EMPLOYEE')")
    @GetMapping
    public ResponseEntity getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll());
    }

    /**
     * Delete a category from the database
     * @param id {@link Long} id of the Category to delete
     * @return {@link ResponseEntity} with Status Code
     */
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Update a category
     * @param category {@link Category} Category to update
     * @return {@link ResponseEntity} with Status Code and updated Category
     */
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PutMapping
    public ResponseEntity updateCategory(@Valid @RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategory(category));
    }

    /**
     * Create a new Category
     * @param category {@link Category} category to create
     * @return {@link ResponseEntity} with Status Code and created Category
     */
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity createCategory(@Valid @RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(category));
    }
}
