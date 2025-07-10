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

    @Query(
            "SELECT b FROM Book b " +
                    "WHERE (:title IS NULL OR b.title LIKE %:keyword%)" +
                    "AND (:author IS NULL OR b.author LIKE %:keyword%) " +
                    "AND (:publisher IS NULL OR b.publisher LIKE %:keyword%) " +
                    "AND (:genre IS NULL OR b.genre LIKE %:keyword%)"
    )
    List<Book> searchBooksByKeyword(@Param("keyword") String keyword);
}
