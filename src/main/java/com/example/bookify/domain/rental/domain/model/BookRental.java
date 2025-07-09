package com.example.bookify.domain.rental.domain.model;

import com.example.bookify.domain.book.entity.Book;
import com.example.bookify.domain.user.domain.model.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "book_rentals")
public class BookRental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime rentedAt;
    private LocalDateTime dueAt;
    private LocalDateTime returnedAt;

    @Enumerated(EnumType.STRING)
    private RentalStatus status;

    private LocalDateTime createAt;
    private LocalDateTime updateAtl;


}
