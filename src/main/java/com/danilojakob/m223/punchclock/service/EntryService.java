package com.danilojakob.m223.punchclock.service;

import com.danilojakob.m223.punchclock.domain.ApplicationUser;
import com.danilojakob.m223.punchclock.exceptions.ConfirmedException;
import com.danilojakob.m223.punchclock.repository.EntryRepository;
import com.danilojakob.m223.punchclock.domain.Entry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryService {
    private EntryRepository entryRepository;

    public EntryService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public Entry createEntry(Entry entry) {
        return entryRepository.saveAndFlush(entry);
    }

    public List<Entry> findAll() {
        return entryRepository.findAll();
    }

    public List<Entry> findByUsername(String username) {
        return entryRepository.findByUsername(username);
    }

    public void deleteById(Long id) throws ConfirmedException {
        // Check if entry is already confirmed
        if (checkIfEntryIsConfirmed(id)) {
            throw new ConfirmedException("Entry already Confirmed");
        }
        entryRepository.deleteById(id);
    }

    public Entry findByApplicationUserId(Long id) {
        return entryRepository.findByApplicationUserId(id);
    }

    public Entry findById(Long id) {
        return entryRepository.findById(id).get();
    }

    /**
     * Confirm an entry
     * @param id {@link Long} id of the entry
     * @return {@link Entry} updated entry
     */
    public Entry confirmEntry(Long id) {
        Entry entry = entryRepository.getOne(id);
        entry.setConfirmed(true);
        return entryRepository.saveAndFlush(entry);
    }

    public Entry updateEntry(Entry entry) throws ConfirmedException {
        if (checkIfEntryIsConfirmed(entry.getId())) {
            throw new ConfirmedException("Entry is already confirmed");
        }
        return entryRepository.saveAndFlush(entry);
    }

    public boolean checkIfEntryIsConfirmed(Long id) {
        return entryRepository.getOne(id).getConfirmed();
    }
}
