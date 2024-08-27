package com.example.smit_1_backend.api;

import com.example.smit_1_backend.dtos.BookDto;
import com.example.smit_1_backend.dtos.SmitResponse;
import com.example.smit_1_backend.services.BookService;
import com.example.smit_1_backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @PostMapping("/user/{userUuid}")
    @ResponseStatus(HttpStatus.OK)
    public SmitResponse addUserBook(@PathVariable UUID userUuid, @RequestBody BookDto bookDto) {
        return bookService.saveBook(userUuid, bookDto);
    }

    @PutMapping("/user/{userUuid}")
    @ResponseStatus(HttpStatus.OK)
    public SmitResponse saveUserBook(@PathVariable UUID userUuid, @RequestBody BookDto bookDto) {
        return bookService.saveBook(userUuid, bookDto);
    }

    @GetMapping("/user/{userUuid}")
    @ResponseStatus(HttpStatus.OK)
    public SmitResponse getUserBooks(@PathVariable UUID userUuid) {
        return bookService.getBooksByUserUuid(userUuid);
    }

    @GetMapping("/user/{userUuid}/reserved")
    @ResponseStatus(HttpStatus.OK)
    public SmitResponse getReservedBooks(@PathVariable UUID userUuid) {
        return bookService.getReservedBooksByUserUuid(userUuid);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public SmitResponse getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/user/{userUuid}/books")
    @ResponseStatus(HttpStatus.OK)
    public SmitResponse getOtherUsersBooks(@PathVariable UUID userUuid) {
        return bookService.getOtherUsersBooks(userUuid);
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public SmitResponse getBook(@PathVariable UUID uuid) {
        return bookService.getBookByUuid(uuid);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public SmitResponse deleteBook(@PathVariable UUID uuid) {
        return bookService.deleteBookByUuid(uuid);
    }

}
