package com.danilojakob.m223.punchclock.repository;

import com.danilojakob.m223.punchclock.domain.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EntryRepository extends JpaRepository<Entry, Long> {

}
