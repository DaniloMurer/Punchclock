package com.danilojakob.m223.punchclock.controller;

import com.danilojakob.m223.punchclock.domain.ApplicationUser;
import com.danilojakob.m223.punchclock.domain.Category;
import com.danilojakob.m223.punchclock.domain.Entry;
import com.danilojakob.m223.punchclock.exceptions.ConfirmedException;
import com.danilojakob.m223.punchclock.service.CategoryService;
import com.danilojakob.m223.punchclock.service.EntryService;
import com.danilojakob.m223.punchclock.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/entries")
public class EntryController {
    private EntryService entryService;
    private UserService userService;
    private CategoryService categoryService;

    public EntryController(EntryService entryService, UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.entryService = entryService;
        this.categoryService = categoryService;
    }

    /**
     * Get user specific entries
     * @param principal {@link Principal} Principal of the user (JWT Token)
     * @return {@link ResponseEntity} with Status Code and all user specific entries
     */
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity getEntries(Principal principal) {
        return ResponseEntity.status(HttpStatus.OK).body(entryService.findByUsername(principal.getName()));
    }

    /**
     * Create a new Entry
     * @param entry {@link Entry} to create
     * @param principal {@link Principal} Principal of the user (JWT Token)
     * @return {@link ResponseEntity} with Status Code and created Entry
     */
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity createEntry(@Valid @RequestBody Entry entry, Principal principal) {

        // Get Category
        Category category = categoryService.findByName(entry.getCategory().getName());
        entry.setCategory(category);
        // Get the user from the request and add it to the entry
        ApplicationUser applicationUser = userService.findByUsername(principal.getName());
        entry.setApplicationUser(applicationUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(entryService.createEntry(entry));
    }

    /**
     * Delete Enrty
     * @param id {@link Long} id of the Entry to delete
     * @return {@link ResponseEntity} with Status Code
     */
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteEntry(@PathVariable Long id, Principal principal) {

        Entry entryToUpdate = entryService.findById(id);
        ApplicationUser user = userService.findByUsername(principal.getName());

        if (entryToUpdate.getApplicationUser().getId() != user.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot delete entry from another User!");
        }

        try {
            entryService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ConfirmedException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    /**
     * Update Entry
     * @param entry {@link Entry} entry to update
     * @param principal {@link Principal} principal of the user (JWT Token)
     * @return {@link ResponseEntity} with Status code and updated entry
     */
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMINISTRATOR')")
    @PutMapping
    public ResponseEntity updateEntry(@Valid @RequestBody Entry entry, Principal principal) {
        ApplicationUser user = userService.findByUsername(principal.getName());
        Category category = categoryService.findByName(entry.getCategory().getName());
        Entry entryToUpdate = entryService.findById(entry.getId());

        if (entryToUpdate.getApplicationUser().getId() != user.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot udpate Entry of another User!");
        }

        if (entry.getConfirmed() != entryToUpdate.getConfirmed()) {
            entry.setConfirmed(entryToUpdate.getConfirmed());
        }
        try {
            entry.setCategory(category);
            entry.setApplicationUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(entryService.updateEntry(entry));
        } catch (ConfirmedException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    /**
     * Set an entry as confirmed
     * @param id {@link Long} id of the entry to confirm
     * @return {@link ResponseEntity} with Status Code and confirmed Entry
     */
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PutMapping("/confirm/{id}")
    public ResponseEntity confirmEntry(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(entryService.confirmEntry(id));
    }

    /**
     * Get all Entries from database regardless of the current user
     * @return {@link ResponseEntity} with Status Code and all entries
     */
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @GetMapping("/getAll")
    public ResponseEntity getAllEntries() {
        return ResponseEntity.status(HttpStatus.OK).body(entryService.findAll());
    }
}
