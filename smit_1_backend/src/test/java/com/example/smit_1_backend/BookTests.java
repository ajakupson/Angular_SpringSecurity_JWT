package com.example.smit_1_backend;

import com.example.smit_1_backend.dtos.BookDto;
import com.example.smit_1_backend.dtos.SmitResponse;
import com.example.smit_1_backend.entities.Book;
import com.example.smit_1_backend.entities.BookReservation;
import com.example.smit_1_backend.entities.User;
import com.example.smit_1_backend.exceptions.AppException;
import com.example.smit_1_backend.repositories.BookRepository;
import com.example.smit_1_backend.repositories.UserRepository;
import com.example.smit_1_backend.services.BookService;
import com.example.smit_1_backend.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookTests {
    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void testShouldNotSaveBook() {
        when(bookRepository.findByTitleAndAuthorAndUserUuid(
                anyString(),
                anyString(),
                any(UUID.class)
        )).thenReturn(Optional.of(new Book()));

        BookDto bookDto = new BookDto(UUID.randomUUID(), "title", "author");

        try {
            bookService.saveBook(UUID.randomUUID(), bookDto);
        } catch (AppException appException) {
            assertEquals(
                    "Book with such title and author already exists!",
                    appException.getMessage(),
                    "Exception message must match"
            );
        }
    }

    @Test
    public void testShouldSaveBook() {
        when(bookRepository.findByTitleAndAuthorAndUserUuid(
                anyString(),
                anyString(),
                any(UUID.class)
        )).thenReturn(Optional.empty());

        BookDto bookDto = new BookDto(UUID.randomUUID(), "title", "author");

        SmitResponse smitResponse = bookService.saveBook(UUID.randomUUID(), bookDto);

        assertTrue(smitResponse.isSuccess(), "SmitResponse success should be true");

        assertEquals("Book successfully saved!", smitResponse.getMessage(), "Messages must match");
    }

    @Test
    public void testGetBooksByUserUuid() {
        int numberOfBooks = 5;

        List<Book> books = new ArrayList<>(numberOfBooks);
        for (int i = 0; i < numberOfBooks; i++) {
            books.add(new Book());
        }

        when(bookRepository.findByUserUuid(any(UUID.class))).thenReturn(books);

        SmitResponse smitResponse = bookService.getBooksByUserUuid(UUID.randomUUID());
        List<Book> retrievedBooks = (List<Book>) smitResponse.getData();

        assertEquals(5, retrievedBooks.stream().count(), "Books count should be 5");
    }

    @Test
    public void testGetReservedBooksByUserUuid() {
        int numberOfBooks = 5;

        List<Book> books = new ArrayList<>(numberOfBooks);
        for (int i = 0; i < numberOfBooks; i++) {
            books.add(new Book());
        }

        when(bookRepository.findBookByReservationUserUuid(any(UUID.class))).thenReturn(books);

        SmitResponse smitResponse = bookService.getReservedBooksByUserUuid(UUID.randomUUID());
        List<Book> retrievedBooks = (List<Book>) smitResponse.getData();

        assertEquals(5, retrievedBooks.stream().count(), "Books count should be 5");
    }

    @Test
    public void testShouldNotDeleteBook() {
        Set<BookReservation> bookReservations = new HashSet<>();
        BookReservation bookReservation = new BookReservation(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDate.of(2024, 8, 13),
                LocalDate.of(2024, 9, 10),
                true,
                false,
                false,
                true
        );

        bookReservations.add(bookReservation);

        Book book = new Book(
                UUID.randomUUID(),
                "title",
                "author",
                UUID.randomUUID(),
                bookReservations,
                new User()
        );

        when(bookRepository.findByUuid(any(UUID.class))).thenReturn(Optional.of(book));

        try {
            bookService.deleteBookByUuid(UUID.randomUUID());
        } catch (AppException appException) {
            assertEquals(
                    "Books that are not returned cannot be deleted!",
                    appException.getMessage(),
                    "Exception message must match"
            );
        }
    }

    @Test
    public void testShouldDeleteBook() {
        Set<BookReservation> bookReservations = new HashSet<>();

        Book book = new Book(
                UUID.randomUUID(),
                "title",
                "author",
                UUID.randomUUID(),
                bookReservations,
                new User()
        );

        when(bookRepository.findByUuid(any(UUID.class))).thenReturn(Optional.of(book));

        SmitResponse smitResponse = bookService.deleteBookByUuid(UUID.randomUUID());

        assertTrue(
                smitResponse.isSuccess(),
                "SmitResponse success should be true"
        );

        assertEquals(
                "Book successfully deleted!",
                smitResponse.getMessage(),
                "SmitResponse is success should be true"
        );
    }
}
