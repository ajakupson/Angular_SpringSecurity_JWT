package com.example.smit_1_backend.services;

import com.example.smit_1_backend.dtos.ReservedBookDto;
import com.example.smit_1_backend.dtos.SmitResponse;
import com.example.smit_1_backend.entities.Book;
import com.example.smit_1_backend.entities.BookReservation;
import com.example.smit_1_backend.exceptions.AppException;
import com.example.smit_1_backend.mappers.BookReservationMapper;
import com.example.smit_1_backend.repositories.BookRepository;
import com.example.smit_1_backend.repositories.BookReservationRepository;
import com.example.smit_1_backend.utils.xConstant;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.joda.time.Weeks;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookReservationService {
    private static final Logger logger = LogManager.getLogger(BookReservationService.class);

    private final BookReservationRepository bookReservationRepository;
    private final BookReservationMapper bookReservationMapper;
    private final BookRepository bookRepository;

    public SmitResponse addBookReservation(ReservedBookDto bookToReserveDto) {
        logger.info(String.format(
                "Trying to add book reservation: Book UUID: %s, user UUID: %s, start date: %s, end date %s",
                bookToReserveDto.bookUuid(),
                bookToReserveDto.userUuid(),
                bookToReserveDto.startDate(),
                bookToReserveDto.endDate()
        ));

        BookReservation bookReservation = bookReservationMapper.dtoToBookReservation(bookToReserveDto);

        Optional<Book> oBook = this.bookRepository.findByUuid(bookReservation.getBookUuid());
        if (!oBook.isPresent()) {
            throw new AppException("Book not found", HttpStatus.NOT_FOUND);
        }

        java.time.LocalDate bookReservationStartDate = bookReservation.getStartDate();
        java.time.LocalDate bookReservationEndDate = bookReservation.getEndDate();

        org.joda.time.LocalDate bookReservationJodaStartDate = new org.joda.time.LocalDate(
                bookReservationStartDate.getYear(),
                bookReservationStartDate.getMonthValue(),
                bookReservationStartDate.getDayOfMonth()
        );
        org.joda.time.LocalDate bookReservationJodaEndDate = new org.joda.time.LocalDate(
                bookReservationEndDate.getYear(),
                bookReservationEndDate.getMonthValue(),
                bookReservationEndDate.getDayOfMonth()
        );

        Book book = oBook.get();
        Set<BookReservation> allBookReservations = book.getBookReservations();

        allBookReservations.stream()
                .filter(br -> !br.getIsCancelled() && !br.getIsReturned())
                .forEach(br -> {
                    java.time.LocalDate brStartDate = br.getStartDate();

                    if (brStartDate.isBefore(bookReservationEndDate)) {
                        return;
                    }

                    org.joda.time.LocalDate brJodaEndDate = new org.joda.time.LocalDate(
                            brStartDate.getYear(),
                            brStartDate.getMonthValue(),
                            brStartDate.getDayOfMonth()
                    );

                    int brWeeksBetween = Weeks.weeksBetween(bookReservationJodaStartDate, brJodaEndDate).getWeeks();
                    System.out.println("======");
                    System.out.println(brWeeksBetween + " " + bookReservationJodaStartDate.toString() + " " + brJodaEndDate.toString());
                    System.out.println("======");

                    if (brWeeksBetween < xConstant.MIN_BORROWING_PERIOD_IN_WEEKS) {
                        throw new AppException("Borrowing period must be not less than 4 weeks", HttpStatus.BAD_REQUEST);
                    }
                });

        int bookReservationWeeksBetween = Weeks.weeksBetween(
                bookReservationJodaStartDate,
                bookReservationJodaEndDate
        ).getWeeks();

        if (bookReservationWeeksBetween < xConstant.MIN_BORROWING_PERIOD_IN_WEEKS) {
            throw new AppException("4 weeks is minimum period for borrowing book", HttpStatus.BAD_REQUEST);
        }

        BookReservation savedBookReservation = bookReservationRepository.save(bookReservation);

        logger.info("Book reservation successfully added!");

        return new SmitResponse(
          true,
          HttpStatus.OK,
          "Book reservation successfully added!",
          savedBookReservation
        );
    }

    public SmitResponse updateBookReservation(ReservedBookDto bookToReserveDto) {
        logger.info("Trying to update book reservation: {}", bookToReserveDto);

        BookReservation bookReservation = bookReservationMapper.dtoToBookReservation(bookToReserveDto);
        BookReservation savedBookReservation = bookReservationRepository.save(bookReservation);

        logger.info("Book reservation successfully updated: {}", bookToReserveDto);

        return new SmitResponse(
                true,
                HttpStatus.OK,
                "Book reservation successfully updated!",
                savedBookReservation
        );
    }
}
