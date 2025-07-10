package com.example.bookify.domain.book.domain.service;

import com.example.bookify.domain.book.domain.model.Book;
import com.example.bookify.domain.book.domain.repository.BookRepository;
import com.example.bookify.domain.book.domain.dto.BookRequestDto;
import com.example.bookify.domain.book.domain.dto.BookResponseDto;
import com.example.bookify.global.common.exception.enums.ExceptionCode;
import com.example.bookify.global.common.exception.exceptionclass.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    // 도서 등록
    public BookResponseDto createBook(BookRequestDto request) {
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .publisher(request.getPublisher())
                .genre(request.getGenre())
                .stock(request.getStock())
                .build();
        Book saved = bookRepository.save(book);
        return BookResponseDto.from(saved);
    }

    // 전체 도서 조회 (삭제되지 않은 것만)
    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAllByIsDeletedFalse()
                .stream()
                .map(BookResponseDto::from)
                .toList();
    }

    // 단일 도서 조회
    public BookResponseDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .filter(b -> !b.isDeleted())
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_BOOK));
        return BookResponseDto.from(book);
    }

    // 도서 검색 조회
    public List<BookResponseDto> searchBooks(String title, String author, String publisher, String genre) {
        String keyword = buildKeyword(title, author, publisher, genre);
        return bookRepository.searchBooksByKeyword(keyword).stream()
                .filter(book -> !book.isDeleted())
                .map(BookResponseDto::from)
                .toList();
    }

    // 도서 정보 수정
    public BookResponseDto updateBook(Long id, BookRequestDto request) {
        Book book = bookRepository.findById(id)
                .filter(b -> !b.isDeleted())
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_BOOK));
        book.update(request.getTitle(), request.getPublisher(), request.getGenre(), request.getStock());
        return BookResponseDto.from(book);
    }

    // 도서 삭제 (소프트 삭제)
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .filter(b -> !b.isDeleted())
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_BOOK));
        book.softDelete();
    }

    // 키워드 빌더
    private String buildKeyword(String title, String author, String publisher, String genre) {
        return (title != null) ? title :
               (author != null) ? author :
               (publisher != null) ? publisher :
               (genre != null) ? genre : "";
    }
}
