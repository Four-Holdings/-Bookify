package com.example.bookify.domain.book.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookRequestDto {
    private String title;
    private String author;
    private String publisher;
    private String genre;
    private Integer stock;
}
