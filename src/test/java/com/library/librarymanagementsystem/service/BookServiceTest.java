package com.library.librarymanagementsystem.service;

import com.library.librarymanagementsystem.exception.ResourceNotFoundException;
import com.library.librarymanagementsystem.model.Book;
import com.library.librarymanagementsystem.repository.BookRepository;
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
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetBookById() {
        Book book = new Book();
        book.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = bookService.getBookById(1L);
        assertTrue(foundBook.isPresent());
        assertEquals(book.getId(), foundBook.get().getId());
    }

    @Test
    public void testAddBook() {
        Book book = new Book();
        book.setTitle("Test Book");
        when(bookRepository.save(book)).thenReturn(book);

        Book savedBook = bookService.addBook(book);
        assertNotNull(savedBook);
        assertEquals("Test Book", savedBook.getTitle());
    }

    @Test
    public void testUpdateBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Original Title");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        Book bookDetails = new Book();
        bookDetails.setTitle("Updated Title");
        Book updatedBook = bookService.updateBook(1L, bookDetails);
        assertEquals("Updated Title", updatedBook.getTitle());
    }

    @Test
    public void testDeleteBook() {
        Book book = new Book();
        book.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.deleteBook(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetBookByIdNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.getBookById(1L);
        });
    }
}