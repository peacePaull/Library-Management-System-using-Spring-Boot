package com.library.librarymanagementsystem.service;

import com.library.librarymanagementsystem.model.Book;
import com.library.librarymanagementsystem.model.BorrowingRecord;
import com.library.librarymanagementsystem.model.Patron;
import com.library.librarymanagementsystem.repository.BorrowingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowingRecordService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        BorrowingRecord borrowingRecord = BorrowingRecord.builder()
                .book(new Book(bookId))
                .patron(new Patron(patronId))
                .borrowingDate(LocalDate.now())
                .build();
        return borrowingRecordRepository.save(borrowingRecord);
    }

    @Transactional
    public Optional<BorrowingRecord> returnBook(Long bookId, Long patronId) {
        return borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId)
                .map(record -> {
                    record.setReturnDate(LocalDate.now());
                    return borrowingRecordRepository.save(record);
                });
    }
}