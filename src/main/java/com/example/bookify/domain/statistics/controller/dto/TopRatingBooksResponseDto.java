package com.example.bookify.domain.statistics.controller.dto;

import com.example.bookify.domain.statistics.domain.model.Statistics;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class TopRatingBooksResponseDto {

    List<BookRankingDto> topRatingBooks;

    public static TopRatingBooksResponseDto formPage(Page<Statistics> page) {
        List<BookRankingDto> pages =
                page.getContent().stream()
                        .map(statistics -> BookRankingDto.builder()
                                .ratingRank(statistics.getReviewRank())
                                .bookId(statistics.getBook().getId())
                                .title(statistics.getBook().getTitle())
                                .author(statistics.getBook().getAuthor())
                                .publisher(statistics.getBook().getPublisher())
                                .genre(statistics.getBook().getGenre())
                                .build()
                        )
                        .toList();

        return TopRatingBooksResponseDto
                .builder()
                .topRatingBooks(pages)
                .build();
    }
}
