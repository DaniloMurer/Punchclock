package com.danilojakob.m223.punchclock.controller;

import com.danilojakob.m223.punchclock.domain.Entry;
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

    public EntryController(EntryService entryService, UserService userService) {
        this.userService = userService;
        this.entryService = entryService;
    }

    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity getEntries(Principal principal) {
        return ResponseEntity.status(HttpStatus.OK).body(entryService.findByUsername(principal.getName()));
    }

    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity createEntry(@Valid @RequestBody Entry entry) {
        return ResponseEntity.status(HttpStatus.CREATED).body(entryService.createEntry(entry));
    }

    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteEntry(@PathVariable Long id) {
        try {
            entryService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMINISTRATOR')")
    @PutMapping
    public ResponseEntity updateEntry(@Valid @RequestBody Entry entry) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(entryService.updateEntry(entry));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PutMapping("/confirm/{id}")
    public ResponseEntity confirmEntry(@PathVariable Long id) {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @GetMapping("/getAll")
    public ResponseEntity getAllEntries() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
