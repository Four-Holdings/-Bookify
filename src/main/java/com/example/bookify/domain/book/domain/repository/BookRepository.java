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


    @Query(
            value = "SELECT * FROM books " +
                    "WHERE MATCH(title, author, publisher, genre) " +
                    "AGAINST (:keyword IN NATURAL LANGUAGE MODE) " +
                    "AND is_deleted = false",
            countQuery = "SELECT COUNT(*) FROM books " +
                    "WHERE MATCH(title, author, publisher, genre) " +
                    "AGAINST (:keyword IN NATURAL LANGUAGE MODE) " +
                    "AND is_deleted = false",
            nativeQuery = true
    )
    Page<Book> fullTextSearch(@Param("keyword") String keyword, Pageable pageable);
}
