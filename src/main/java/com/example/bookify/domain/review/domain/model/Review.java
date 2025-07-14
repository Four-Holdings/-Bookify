package com.example.bookify.domain.review.domain.model;

import com.example.bookify.domain.book.domain.model.Book;
import com.example.bookify.domain.user.domain.model.User;
import com.example.bookify.global.common.jpa.SoftDeleteEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;




@Entity
@Getter
@Table(name = "reviews")
@NoArgsConstructor
public class Review extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    @Column(nullable = false)
    private int grades;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;


    public Review(User user, Book book, int grades, String content) {
        this.user = user;
        this.book = book;
        this.grades = grades;
        this.content = content;
    }

    public void updateReview(Integer grades, String content){
        this.grades = grades;
        this.content = content;
    }
}
