package com.example.bookify.domain.book.domain.service;

import com.example.bookify.domain.book.domain.model.Book;
import com.example.bookify.domain.book.domain.repository.BookRepository;
import com.example.bookify.domain.book.domain.dto.BookRequestDto;
import com.example.bookify.domain.book.domain.dto.BookResponseDto;
import com.example.bookify.global.common.exception.enums.ExceptionCode;
import com.example.bookify.global.common.exception.exceptionclass.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public BookResponseDto createBook(BookRequestDto request) {
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .publisher(request.getPublisher())
                .genre(request.getGenre())
                .stock(request.getStock() != null ? request.getStock() : 0)
                .build();
        Book saved = bookRepository.save(book);
        return BookResponseDto.from(saved);
    }

    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAllByIsDeletedFalse()
                .stream()
                .map(BookResponseDto::from)
                .toList();
    }

    public BookResponseDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .filter(b -> !b.isDeleted())
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_BOOK));
        return BookResponseDto.from(book);
    }

    public List<BookResponseDto> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllBooks();
        }

        List<Book> books = bookRepository.searchBooksByList(keyword);
        return books.stream()
                .filter(book -> !book.isDeleted())
                .map(BookResponseDto::from)
                .toList();
    }

    public BookResponseDto updateBook(Long id, BookRequestDto request) {
        Book book = bookRepository.findById(id)
                .filter(b -> !b.isDeleted())
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_BOOK));

        book.update(
                request.getTitle(),
                request.getAuthor(),
                request.getPublisher(),
                request.getGenre(),
                request.getStock()
        );
        return BookResponseDto.from(book);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .filter(b -> !b.isDeleted())
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_BOOK));
        book.softDelete();
    }
}

