package com.library.librarymanagementsystem.service;

import com.library.librarymanagementsystem.model.Patron;
import com.library.librarymanagementsystem.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    @Autowired
    private PatronRepository patronRepository;

    @Cacheable("patrons")
    @Transactional(readOnly = true)
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Cacheable(value = "patrons", key = "#id")
    @Transactional(readOnly = true)
    public Optional<Patron> getPatronById(Long id) {
        return patronRepository.findById(id);
    }

    @CacheEvict(value = "patrons", allEntries = true)
    @Transactional
    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @CacheEvict(value = "patrons", key = "#id")
    @Transactional
    public Patron updatePatron(Long id, Patron patronDetails) {
        return patronRepository.findById(id).map(patron -> {
            patron.setName(patronDetails.getName());
            patron.setContactInformation(patronDetails.getContactInformation());
            return patronRepository.save(patron);
        }).orElseThrow(() -> new RuntimeException("Patron not found"));
    }

    @CacheEvict(value = "patrons", key = "#id")
    @Transactional
    public void deletePatron(Long id) {
        patronRepository.deleteById(id);
    }
}