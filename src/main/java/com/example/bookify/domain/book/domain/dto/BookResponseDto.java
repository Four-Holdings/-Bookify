package com.example.bookify.domain.book.domain.dto;

import com.example.bookify.domain.book.domain.model.Book;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String genre;
    private Integer stock;
    private Integer ranking;

    public static BookResponseDto from(Book book) {
        return BookResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .genre(book.getGenre())
                .stock(book.getStock())
                .ranking(book.getRanking())
                .build();
    }
}
