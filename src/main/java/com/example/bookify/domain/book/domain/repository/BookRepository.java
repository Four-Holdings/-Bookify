package com.example.bookify.domain.book.domain.repository;

import com.example.bookify.domain.book.domain.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByIsDeletedFalse();

    @Query("SELECT b FROM Book b " +
            "WHERE (:keyword IS NULL OR" +
            " b.title LIKE %:keyword% OR" +
            " b.author LIKE %:keyword% OR" +
            " b.publisher LIKE %:keyword% OR" +
            " b.genre LIKE %:keyword%) " +
            "AND b.isDeleted = false")

    List<Book> searchBooksByList(@Param("keyword") String keyword);
}
