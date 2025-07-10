package com.example.bookify.domain.book.domain.model;

import com.example.bookify.global.common.jpa.SoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Book extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(length = 100)
    private String publisher;

    @Column(length = 50)
    private String genre;

    @Column(nullable = false)
    private Integer stock = 0;

    @Column
    private Integer ranking;

    public void update(String title, String author, String publisher, String genre, Integer stock) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.stock = stock;
    }

    public void softDelete() {
        this.delete();
    }
}
