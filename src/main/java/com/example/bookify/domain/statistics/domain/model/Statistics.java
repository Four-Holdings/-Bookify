package com.example.bookify.domain.statistics.domain.model;

import com.example.bookify.domain.book.entity.Book;
import com.example.bookify.global.common.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "statistics")
@NoArgsConstructor
public class Statistics extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statisticsId;

    @Column(nullable = false)
    private Long reviewRank;

    @Column(nullable = false)
    private double ratingAvg;

    @Column(nullable = false)
    private Long reviewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public Statistics(Long reviewRank, double ratingAvg, Long reviewCount, Book book) {
        this.reviewRank = reviewRank;
        this.ratingAvg = ratingAvg;
        this.reviewCount = reviewCount;
        this.book = book;
    }

}
