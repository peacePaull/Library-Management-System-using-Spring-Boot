package com.library.librarymanagementsystem.service;

import com.library.librarymanagementsystem.exception.ResourceNotFoundException;
import com.library.librarymanagementsystem.model.Book;
import com.library.librarymanagementsystem.model.BorrowingRecord;
import com.library.librarymanagementsystem.model.Patron;
import com.library.librarymanagementsystem.repository.BorrowingRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BorrowingRecordServiceTest {

    @InjectMocks
    private BorrowingRecordService borrowingRecordService;

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBorrowBook() {
        BorrowingRecord borrowingRecord = BorrowingRecord.builder()
                .book(new Book(1L))
                .patron(new Patron(1L))
                .borrowingDate(LocalDate.now())
                .build();
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        BorrowingRecord savedBorrowingRecord = borrowingRecordService.borrowBook(1L, 1L);
        assertNotNull(savedBorrowingRecord);
        assertEquals(1L, savedBorrowingRecord.getBook().getId());
        assertEquals(1L, savedBorrowingRecord.getPatron().getId());
    }

    @Test
    public void testReturnBook() {
        BorrowingRecord borrowingRecord = BorrowingRecord.builder()
                .book(new Book(1L))
                .patron(new Patron(1L))
                .borrowingDate(LocalDate.now())
                .build();
        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(1L, 1L))
                .thenReturn(Optional.of(borrowingRecord));
        when(borrowingRecordRepository.save(borrowingRecord)).thenReturn(borrowingRecord);

        Optional<BorrowingRecord> returnedBorrowingRecord = borrowingRecordService.returnBook(1L, 1L);
        assertTrue(returnedBorrowingRecord.isPresent());
        assertEquals(LocalDate.now(), returnedBorrowingRecord.get().getReturnDate());
    }

    @Test
    public void testReturnBookNotFound() {
        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(1L, 1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            borrowingRecordService.returnBook(1L, 1L);
        });
    }
}