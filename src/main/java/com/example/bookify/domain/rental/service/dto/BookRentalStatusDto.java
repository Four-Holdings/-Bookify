package com.example.bookify.domain.rental.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookRentalStatusDto {
    private Long bookId;
    private boolean isRented;


}
