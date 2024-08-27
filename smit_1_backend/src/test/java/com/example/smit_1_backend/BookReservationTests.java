package com.example.smit_1_backend;

import com.example.smit_1_backend.dtos.ReservedBookDto;
import com.example.smit_1_backend.dtos.SmitResponse;
import com.example.smit_1_backend.entities.Book;
import com.example.smit_1_backend.entities.BookReservation;
import com.example.smit_1_backend.entities.User;
import com.example.smit_1_backend.exceptions.AppException;
import com.example.smit_1_backend.repositories.BookRepository;
import com.example.smit_1_backend.repositories.BookReservationRepository;
import com.example.smit_1_backend.repositories.UserRepository;
import com.example.smit_1_backend.services.BookReservationService;
import com.example.smit_1_backend.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookReservationTests {
    @Autowired
    private BookReservationService bookReservationService;

    @MockBean
    private BookReservationRepository bookReservationRepository;

    @MockBean
    BookRepository bookRepository;

    @Test
    public void testShouldNotAddBookReservation() {
        ReservedBookDto reservedBookDto = new ReservedBookDto(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                "2024-08-13",
                "2024-09-10",
                false,
                false,
                false,
                false
        );

        Set<BookReservation> bookReservations = new HashSet<>();
        BookReservation bookReservation = new BookReservation(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 6, 15),
                true,
                false,
                false,
                true
        );

        bookReservations.add(bookReservation);

        bookReservation = new BookReservation(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDate.of(2024, 8, 25),
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
            bookReservationService.addBookReservation(reservedBookDto);
        } catch (AppException appException) {
            assertEquals(
                    "Borrowing period must be not less than 4 weeks",
                    appException.getMessage(),
                    "Exception message should match"
            );
        }

        reservedBookDto = new ReservedBookDto(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                "2024-09-11",
                "2024-09-15",
                false,
                false,
                false,
                false
        );

        try {
            bookReservationService.addBookReservation(reservedBookDto);
        } catch (AppException appException) {
            assertEquals(
                    "4 weeks is minimum period for borrowing book",
                    appException.getMessage(),
                    "Exception message should match"
            );
        }
    }

    @Test
    public void testShouldAddBookReservation() {
        ReservedBookDto reservedBookDto = new ReservedBookDto(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                "2024-09-01",
                "2024-11-01",
                false,
                false,
                false,
                false
        );

        Book book = new Book(
                UUID.randomUUID(),
                "title",
                "author",
                UUID.randomUUID(),
                new HashSet<>(),
                new User()
        );

        when(bookRepository.findByUuid(any(UUID.class))).thenReturn(Optional.of(book));

        SmitResponse smitResponse = bookReservationService.addBookReservation(reservedBookDto);

        assertTrue(smitResponse.isSuccess(), "SmitResponse success should be true");
        assertEquals(
                "Book reservation successfully added!",
                smitResponse.getMessage(),
                "Message should match"
        );
    }
}
