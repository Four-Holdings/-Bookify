package com.example.bookify.domain.statistics.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookRankingDto {

    private final Long ratingRank;

    private final Long bookId;

    private final String title;

    private final String author;

    private final String publisher;

    private final String genre;


    public BookRankingDto(
            Long ratingRank,
            Long bookId,
            String title,
            String author,
            String publisher,
            String genre
    ) {
        this.ratingRank = ratingRank;
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
    }
}
