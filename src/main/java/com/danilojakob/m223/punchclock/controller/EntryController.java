package com.danilojakob.m223.punchclock.controller;

import com.danilojakob.m223.punchclock.domain.Entry;
import com.danilojakob.m223.punchclock.service.EntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/entries")
public class EntryController {
    private EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @GetMapping
    public ResponseEntity getAllEntries() {
        return ResponseEntity.status(HttpStatus.OK).body(entryService.findAll());
    }

    @PostMapping
    public ResponseEntity createEntry(@Valid @RequestBody Entry entry) {
        return ResponseEntity.status(HttpStatus.CREATED).body(entryService.createEntry(entry));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEntry(@PathVariable Long id) {
        try {
            entryService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updateEntry(@Valid @RequestBody Entry entry) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(entryService.updateEntry(entry));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
