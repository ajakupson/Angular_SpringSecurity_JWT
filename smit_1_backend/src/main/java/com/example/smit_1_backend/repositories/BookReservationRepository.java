package com.example.smit_1_backend.repositories;

import com.example.smit_1_backend.entities.Book;
import com.example.smit_1_backend.entities.BookReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReservationRepository extends JpaRepository<BookReservation, String> {

}
