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
    private Long userId;
    private LocalDateTime rentedAt;
    private LocalDateTime dueAt;
    private LocalDateTime returnAt;
    private RentalStatus status;

    public static BookRentalResponseDto from(BookRental rental) {
        return BookRentalResponseDto.builder()
                .rentalId(rental.getRentalId())
                .bookId(rental.getBook().getId())
                .userId(rental.getUser().getId())
                .rentedAt(rental.getRentedAt())
                .dueAt(rental.getDueAt())
                .returnAt(rental.getReturnedAt())
                .status(rental.getStatus())
                .build();
    }
}
