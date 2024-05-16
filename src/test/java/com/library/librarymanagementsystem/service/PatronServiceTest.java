package com.library.librarymanagementsystem.service;

import com.library.librarymanagementsystem.exception.ResourceNotFoundException;
import com.library.librarymanagementsystem.model.Patron;
import com.library.librarymanagementsystem.repository.PatronRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PatronServiceTest {

    @InjectMocks
    private PatronService patronService;

    @Mock
    private PatronRepository patronRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPatronById() {
        Patron patron = new Patron();
        patron.setId(1L);
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));

        Optional<Patron> foundPatron = patronService.getPatronById(1L);
        assertTrue(foundPatron.isPresent());
        assertEquals(patron.getId(), foundPatron.get().getId());
    }

    @Test
    public void testAddPatron() {
        Patron patron = new Patron();
        patron.setName("Test Patron");
        when(patronRepository.save(patron)).thenReturn(patron);

        Patron savedPatron = patronService.addPatron(patron);
        assertNotNull(savedPatron);
        assertEquals("Test Patron", savedPatron.getName());
    }

    @Test
    public void testUpdatePatron() {
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("Original Name");
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        when(patronRepository.save(patron)).thenReturn(patron);

        Patron patronDetails = new Patron();
        patronDetails.setName("Updated Name");
        Patron updatedPatron = patronService.updatePatron(1L, patronDetails);
        assertEquals("Updated Name", updatedPatron.getName());
    }

    @Test
    public void testDeletePatron() {
        Patron patron = new Patron();
        patron.setId(1L);
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));

        patronService.deletePatron(1L);
        verify(patronRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetPatronByIdNotFound() {
        when(patronRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            patronService.getPatronById(1L);
        });
    }
}
