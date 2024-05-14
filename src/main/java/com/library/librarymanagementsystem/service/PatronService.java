package com.library.librarymanagementsystem.service;

import com.library.librarymanagementsystem.model.Patron;
import com.library.librarymanagementsystem.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    @Autowired
    private PatronRepository patronRepository;

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    public Optional<Patron> getPatronById(Long id) {
        return patronRepository.findById(id);
    }

    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    public Patron updatePatron(Long id, Patron patronDetails) {
        return patronRepository.findById(id).map(patron -> {
            patron.setName(patronDetails.getName());
            patron.setContactInformation(patronDetails.getContactInformation());
            return patronRepository.save(patron);
        }).orElseThrow(() -> new RuntimeException("Patron not found"));
    }

    public void deletePatron(Long id) {
        patronRepository.deleteById(id);
    }
}
