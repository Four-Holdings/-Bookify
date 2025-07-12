package com.example.bookify.domain.statistics.service.dto;

import com.example.bookify.domain.book.domain.model.Book;
import lombok.Getter;

@Getter
public class AvgReviewGradeDto {

    private Book book;

    private Double avgGrade;

    private Long countReview;

    public AvgReviewGradeDto(Book book, Double avgGrade, Long countReview) {
        this.book = book;
        this.avgGrade = avgGrade;
        this.countReview = countReview;
    }


}
