package com.example.bookify.domain.book.repository;

import com.example.bookify.domain.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {

}
