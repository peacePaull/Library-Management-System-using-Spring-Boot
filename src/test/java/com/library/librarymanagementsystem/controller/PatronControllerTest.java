package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.exception.CustomExceptionHandler;
import com.library.librarymanagementsystem.model.Patron;
import com.library.librarymanagementsystem.service.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class PatronControllerTest {

    @InjectMocks
    private PatronController patronController;

    @Mock
    private PatronService patronService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patronController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    public void testGetAllPatrons() throws Exception {
        when(patronService.getAllPatrons()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetPatronById() throws Exception {
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("Test Patron");
        when(patronService.getPatronById(1L)).thenReturn(Optional.of(patron));

        mockMvc.perform(get("/api/patrons/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Patron"));
    }

    @Test
    public void testGetPatronByIdNotFound() throws Exception {
        when(patronService.getPatronById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/patrons/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddPatron() throws Exception {
        Patron patron = new Patron();
        patron.setName("Test Patron");
        when(patronService.addPatron(any(Patron.class))).thenReturn(patron);

        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test Patron\", \"contactInformation\": \"1234567890\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Patron"));
    }

    @Test
    public void testUpdatePatron() throws Exception {
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("Original Name");
        when(patronService.getPatronById(1L)).thenReturn(Optional.of(patron));
        when(patronService.updatePatron(eq(1L), any(Patron.class))).thenReturn(patron);

        mockMvc.perform(put("/api/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Name\", \"contactInformation\": \"9876543210\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    public void testDeletePatron() throws Exception {
        doNothing().when(patronService).deletePatron(1L);

        mockMvc.perform(delete("/api/patrons/1"))
                .andExpect(status().isNoContent());
    }
}