package com.example.smit_1_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(name = "user_uuid", nullable = false)
    private UUID userUuid;

    @OneToMany
    @JoinColumn(name = "book_uuid", referencedColumnName = "uuid", insertable = false, updatable = false)
    @JsonIgnoreProperties({"user_uuid"})
    private Set<BookReservation> bookReservations;

    @OneToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid", insertable = false, updatable = false)
    @JsonIgnoreProperties({"uuid", "password"})
    private User user;
}
