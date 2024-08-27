package com.example.smit_1_backend.services;

import com.example.smit_1_backend.dtos.BookDto;
import com.example.smit_1_backend.dtos.SmitResponse;
import com.example.smit_1_backend.entities.Book;
import com.example.smit_1_backend.entities.User;
import com.example.smit_1_backend.exceptions.AppException;
import com.example.smit_1_backend.mappers.BookMapper;
import com.example.smit_1_backend.mappers.UserMapper;
import com.example.smit_1_backend.repositories.BookRepository;
import com.example.smit_1_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private static final Logger logger = LogManager.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public SmitResponse saveBook(UUID userUuid, BookDto bookDto) {
        logger.info("Trying to save book: {}", bookDto);

        Optional<Book> oBook = bookRepository.findByTitleAndAuthorAndUserUuid(
                bookDto.title(),
                bookDto.author(),
                userUuid
        );

        if (oBook.isPresent()) {
            throw new AppException("Book with such title and author already exists!", HttpStatus.BAD_REQUEST);
        }

        Book book = bookMapper.dtoToBook(bookDto);
        book.setUserUuid(userUuid);

        Book savedBook = bookRepository.save(book);

        logger.info("Book successfully saved: {}", savedBook);

        return new SmitResponse(
                true,
                HttpStatus.OK,
                "Book successfully saved!",
                bookMapper.toBookDto(savedBook)
        );
    }

    public SmitResponse getBooksByUserUuid(UUID userUuuid) {
        logger.info(String.format("Trying to get books by user uuid. UUID: %s", userUuuid));

        List<Book> books = this.bookRepository.findByUserUuid(userUuuid);

        logger.info(String.format("Books count %d", books.stream().count()));

        return new SmitResponse(
          true,
          HttpStatus.OK,
          null,
                books
        );
    }

    public SmitResponse getReservedBooksByUserUuid(UUID userUuid) {
        logger.info(String.format("Trying to get reserved books by userUuid %s", userUuid));

        List<Book> books = this.bookRepository.findBookByReservationUserUuid(userUuid);

        logger.info(String.format("Books count %d", books.stream().count()));

        return new SmitResponse(
                true,
                HttpStatus.OK,
                null,
                books
        );
    }

    public SmitResponse getAllBooks() {
        logger.info("Trying to get all books");

        List<Book> books = this.bookRepository.findAll();

        logger.info(String.format("Books count %d", books.stream().count()));

        return new SmitResponse(
                true,
                HttpStatus.OK,
                null,
                books
        );
    }

    public SmitResponse getBookByUuid(UUID uuid) {
        logger.info(String.format("Trying to get book by UUID: %s", uuid));

        Optional<Book> oBook = this.bookRepository.findByUuid(uuid);

        if (oBook.isPresent()) {
            logger.info(String.format("Book found. UUID: %s", uuid));

            return new SmitResponse(
                    true,
                    HttpStatus.OK,
                    "Book found",
                    oBook.get()
            );
        }

        logger.warn(String.format("Book not found. UUID: %s", uuid));

        return new SmitResponse(
                false,
                HttpStatus.NOT_FOUND,
                "Book not found",
                null
        );
    }

    public SmitResponse getOtherUsersBooks(UUID userUuid) {
        logger.info(String.format("Trying to get other users books where user UUID not equal %s", userUuid));

        List<Book> books = this.bookRepository.getOtherUsersBooks(userUuid);

        logger.info(String.format("Books count %d", books.stream().count()));

        return new SmitResponse(
                true,
                HttpStatus.OK,
                null,
                books
        );
    }

    public SmitResponse deleteBookByUuid(UUID uuid) {
        logger.info(String.format("Trying to delete book by UUID %s", uuid));

        Optional<Book> oBook = this.bookRepository.findByUuid(uuid);

        if (!oBook.isPresent()) {
            throw new AppException("Book not found!", HttpStatus.BAD_REQUEST);
        }

        Book book = oBook.get();

        book.getBookReservations().stream().forEach(br -> {
            if (br.getIsReceived() && !br.getIsReturned()) {
                throw new AppException("Books that are not returned cannot be deleted!", HttpStatus.BAD_REQUEST);
            }
        });

        this.bookRepository.delete(book);

        logger.info("Book successfully deleted!");

        return new SmitResponse(
          true,
          HttpStatus.OK,
          "Book successfully deleted!",
          null
        );
    }
}
