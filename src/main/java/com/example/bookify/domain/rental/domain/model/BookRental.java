package com.example.bookify.domain.rental.domain.model;

import com.example.bookify.domain.book.entity.Book;
import com.example.bookify.domain.user.domain.model.User;
import com.example.bookify.global.common.jpa.SoftDeleteEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "book_rentals")
public class BookRental extends SoftDeleteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime rentedAt;
    @Column(nullable = false)
    private LocalDateTime dueAt;
    private LocalDateTime returnedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RentalStatus status;
}
