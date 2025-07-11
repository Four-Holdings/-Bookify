package com.example.bookify.domain.rental.domain.repository;

import com.example.bookify.domain.rental.domain.model.BookRental;
import com.example.bookify.domain.rental.domain.model.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRentalRepository extends JpaRepository<BookRental, Long> {
    boolean existsByBookIdAndStatus(Long bookId, RentalStatus status);
    List<BookRental> findAllByUserId(Long userId);
}
