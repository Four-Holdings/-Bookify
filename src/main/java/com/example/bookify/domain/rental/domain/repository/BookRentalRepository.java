package com.example.bookify.domain.rental.domain.repository;

import com.example.bookify.domain.rental.domain.model.BookRental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRentalRepository extends JpaRepository<BookRental, Long> {
}
