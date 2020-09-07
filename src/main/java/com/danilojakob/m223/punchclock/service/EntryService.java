package com.danilojakob.m223.punchclock.service;

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

    public void deleteById(Long id) throws Exception {
        entryRepository.deleteById(id);
    }

    public Entry updateEntry(Entry entry) throws Exception {
        return entryRepository.saveAndFlush(entry);
    }
}
