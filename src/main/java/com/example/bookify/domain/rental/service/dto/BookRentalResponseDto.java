package com.example.bookify.domain.rental.service.dto;

import com.example.bookify.domain.rental.domain.model.BookRental;
import com.example.bookify.domain.rental.domain.model.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class BookRentalResponseDto {
    private Long rentalId;
    private Long bookId;
    private String bookTitle;
    private RentalStatus status;
    private LocalDateTime rentedAt;
    private LocalDateTime returnedAt;

    public static BookRentalResponseDto fromEntity(BookRental rental) {
        return BookRentalResponseDto.builder()
                .rentalId(rental.getRentalId())
                .bookId(rental.getBook().getId())
                .bookTitle(rental.getBook().getTitle())
                .status(rental.getStatus())
                .rentedAt(rental.getRentedAt())
                .returnedAt(rental.getReturnedAt())
                .build();
    }
}
