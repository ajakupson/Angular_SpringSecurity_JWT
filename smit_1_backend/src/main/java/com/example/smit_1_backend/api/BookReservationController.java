package com.example.smit_1_backend.api;

import com.example.smit_1_backend.dtos.BookDto;
import com.example.smit_1_backend.dtos.ReservedBookDto;
import com.example.smit_1_backend.dtos.SmitResponse;
import com.example.smit_1_backend.services.BookReservationService;
import com.example.smit_1_backend.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book_reservation")
public class BookReservationController {

    private final BookReservationService bookReservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public SmitResponse addBookReservation(@RequestBody ReservedBookDto reservedBookDto) {
        return bookReservationService.addBookReservation(reservedBookDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public SmitResponse updateBookReservation(@RequestBody ReservedBookDto reservedBookDto) {
        return bookReservationService.updateBookReservation(reservedBookDto);
    }
}
