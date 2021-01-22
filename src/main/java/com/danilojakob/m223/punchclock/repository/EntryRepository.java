package com.danilojakob.m223.punchclock.repository;

import com.danilojakob.m223.punchclock.domain.ApplicationUser;
import com.danilojakob.m223.punchclock.domain.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> findByUsername(String username);

    @Query("select e from Entry e where e.applicationUser.id = ?1")
    Entry findByApplicationUserId(Long id);
}
