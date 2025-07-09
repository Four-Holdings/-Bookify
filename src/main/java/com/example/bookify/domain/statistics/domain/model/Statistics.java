package com.example.bookify.domain.statistics.domain.model;

import com.example.bookify.domain.book.entity.Book;
import com.example.bookify.global.common.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "Statistics")
@NoArgsConstructor
public class Statistics extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statisticsId;

    @Column(nullable = false)
    private Long reviewRank;

    @Column(nullable = false)
    private double rating_avg;

    @Column(nullable = false)
    private Long reviewCount;

    @Column(nullable = false)
    private LocalDateTime month;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

}
