package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.exception.CustomExceptionHandler;
import com.library.librarymanagementsystem.model.Book;
import com.library.librarymanagementsystem.model.BorrowingRecord;
import com.library.librarymanagementsystem.model.Patron;
import com.library.librarymanagementsystem.service.BorrowingRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class BorrowingRecordControllerTest {

    @InjectMocks
    private BorrowingRecordController borrowingRecordController;

    @Mock
    private BorrowingRecordService borrowingRecordService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(borrowingRecordController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    public void testBorrowBook() throws Exception {
        BorrowingRecord borrowingRecord = BorrowingRecord.builder()
                .book(new Book(1L))
                .patron(new Patron(1L))
                .borrowingDate(LocalDate.now())
                .build();
        when(borrowingRecordService.borrowBook(1L, 1L)).thenReturn(borrowingRecord);

        mockMvc.perform(post("/api/borrow/1/patron/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.book.id").value(1L))
                .andExpect(jsonPath("$.patron.id").value(1L))
                .andExpect(jsonPath("$.borrowingDate").value(LocalDate.now().toString()));
    }

    @Test
    public void testReturnBook() throws Exception {
        BorrowingRecord borrowingRecord = BorrowingRecord.builder()
                .book(new Book(1L))
                .patron(new Patron(1L))
                .borrowingDate(LocalDate.now())
                .returnDate(LocalDate.now())
                .build();
        when(borrowingRecordService.returnBook(1L, 1L)).thenReturn(Optional.of(borrowingRecord));

        mockMvc.perform(put("/api/return/1/patron/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.book.id").value(1L))
                .andExpect(jsonPath("$.patron.id").value(1L))
                .andExpect(jsonPath("$.returnDate").value(LocalDate.now().toString()));
    }

    @Test
    public void testReturnBookNotFound() throws Exception {
        when(borrowingRecordService.returnBook(1L, 1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/return/1/patron/1"))
                .andExpect(status().isNotFound());
    }
}