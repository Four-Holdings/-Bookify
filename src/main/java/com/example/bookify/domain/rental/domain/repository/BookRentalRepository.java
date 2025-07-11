package com.example.bookify.domain.rental.domain.repository;

import com.example.bookify.domain.rental.domain.model.BookRental;
import com.example.bookify.domain.rental.domain.model.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRentalRepository extends JpaRepository<BookRental, Long> {
    boolean existsByBookIdAndStatus(Long bookId, RentalStatus status);
    List<BookRental> findByUserId(Long userId);
    Optional<BookRental> findByIdAndUserId(Long rentalId, Long memberId);
}
