package com.example.bookify.domain.rental.domain.model;

import com.example.bookify.domain.book.domain.model.Book;
import com.example.bookify.domain.user.domain.model.User;
import com.example.bookify.global.common.jpa.SoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "book_rentals")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
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
    private RentalStatus status;

    public void returnBook() {
        this.status = RentalStatus.RETURNED;
        this.returnedAt = LocalDateTime.now();
    }

}
