package com.example.smit_1_backend.repositories;

import com.example.smit_1_backend.entities.Book;
import com.example.smit_1_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, String> {
    Optional<Book> findByUuid(UUID uuid);
    Optional<Book> findByTitle(String title);
    Optional<Book> findByAuthor(String author);
    Optional<Book> findByTitleAndAuthorAndUserUuid(String title, String author, UUID userUuid);
    List<Book> findByUserUuid(UUID userUuid);

    @Query("SELECT b FROM Book b WHERE b.uuid IN (" +
            "SELECT br.bookUuid FROM BookReservation br WHERE br.userUuid = :userUuid)")
    List<Book> findBookByReservationUserUuid(UUID userUuid);

    @Query("SELECT b FROM Book b WHERE b.userUuid <> :userUuid")
    List<Book> getOtherUsersBooks(UUID userUuid);
}
