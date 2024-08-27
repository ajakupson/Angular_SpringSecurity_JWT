package com.example.smit_1_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "book_reservation")
public class BookReservation {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID uuid;

    @Column(name = "book_uuid", nullable = false)
    private UUID bookUuid;

    @Column(name = "user_uuid", nullable = false)
    private UUID userUuid;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "is_received", nullable = false)
    private Boolean isReceived;

    @Column(name = "is_returned", nullable = false)
    private Boolean isReturned;

    @Column(name = "is_cancelled", nullable = false)
    private Boolean isCancelled;

    @Column(name = "is_sent", nullable = false)
    private Boolean isSent;
}
