package com.example.bookify.domain.rental.service.dto;

import com.example.bookify.domain.rental.domain.model.BookRental;
import com.example.bookify.domain.rental.domain.model.RentalStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// BookRentalResponseDto.java
@Getter
@Builder
public class BookRentalResponseDto {
    private Long rentalId;
    private Long bookId;
    private Long userId;
    private RentalStatus status;
    private LocalDateTime rentedAt;
    private LocalDateTime dueAt;
    private LocalDateTime returnedAt;

    public static BookRentalResponseDto fromEntity(BookRental rental) {
        return BookRentalResponseDto.builder()
                .rentalId(rental.getRentalId())
                .bookId(rental.getBook().getId())
                .userId(rental.getUser().getId())
                .status(rental.getStatus())
                .rentedAt(rental.getRentedAt())
                .dueAt(rental.getDueAt())
                .returnedAt(rental.getReturnedAt())
                .build();
    }
}
