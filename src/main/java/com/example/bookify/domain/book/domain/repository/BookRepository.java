package com.example.bookify.domain.book.domain.repository;

import com.example.bookify.domain.book.domain.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findAllByIsDeletedFalse(Pageable pageable);

    @Query("SELECT b FROM Book b " +
            "WHERE (:keyword IS NULL OR" +
            " b.title LIKE %:keyword% OR" +
            " b.author LIKE %:keyword% OR" +
            " b.publisher LIKE %:keyword% OR" +
            " b.genre LIKE %:keyword%) " +
            "AND b.isDeleted = false")

    Page<Book> searchBooksByList(@Param("keyword") String keyword, Pageable pageable);
}
