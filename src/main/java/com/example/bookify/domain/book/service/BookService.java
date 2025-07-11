package com.example.bookify.domain.book.service;

import com.example.bookify.domain.book.domain.model.Book;
import com.example.bookify.domain.book.domain.repository.BookRepository;
import com.example.bookify.domain.book.controller.dto.BookRequestDto;
import com.example.bookify.domain.book.controller.dto.BookResponseDto;
import com.example.bookify.global.common.exception.enums.ExceptionCode;
import com.example.bookify.global.common.exception.exceptionclass.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    // 페이징된 전체 도서 조회
    public Page<BookResponseDto> getAllBooks(Pageable pageable) {
        return bookRepository.findAllByIsDeletedFalse(pageable)
                .map(BookResponseDto::from);
    }

    public BookResponseDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .filter(b -> !b.isDeleted())
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_BOOK));
        return BookResponseDto.from(book);
    }

    // 페이징된 도서 검색
    public Page<BookResponseDto> searchBooks(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllBooks(pageable);
        }
        return bookRepository.searchBooksByList(keyword, pageable)
                .map(BookResponseDto::from);
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

