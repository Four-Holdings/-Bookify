package com.example.bookify.domain.book.domain.controller;

import com.example.bookify.domain.book.domain.dto.BookRequestDto;
import com.example.bookify.domain.book.domain.dto.BookResponseDto;
import com.example.bookify.domain.book.domain.service.BookService;
import com.example.bookify.global.common.apiresponse.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // 도서 등록
    @PostMapping
    public ResponseEntity<ResponseDto> createBook(@RequestBody BookRequestDto request) {
        BookResponseDto response = bookService.createBook(request);
        ResponseDto responseDto = new ResponseDto("도서가 등록되었습니다", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    // 도서 전체 조회
    @GetMapping("/all")
    public ResponseEntity<ResponseDto> getAllBooks() {
        List<BookResponseDto> books = bookService.getAllBooks();
        ResponseDto responseDto = new ResponseDto("도서 목록 정보가 조회되었습니다.", books);
        return ResponseEntity.ok(responseDto);
    }

    // 도서 단건 조회
    @GetMapping("/{bookId}")
    public ResponseEntity<ResponseDto> getBookById(@PathVariable("bookId") Long id) {
        BookResponseDto response = bookService.getBookById(id);
        ResponseDto responseDto = new ResponseDto("도서 상세 정보가 조회되었습니다.", response);
        return ResponseEntity.ok(responseDto);
    }

    // 도서 검색
    @GetMapping("/search")
    public ResponseEntity<ResponseDto> searchBooks(@RequestParam(required = false) String keyword) {
        List<BookResponseDto> results = bookService.searchBooks(keyword);
        ResponseDto responseDto = new ResponseDto("도서 검색 결과가 조회되었습니다.", results);
        return ResponseEntity.ok(responseDto);
    }

    // 도서 정보 수정
    @PutMapping("/{bookId}")
    public ResponseEntity<ResponseDto> updateBook(@PathVariable("bookId") Long id, @RequestBody BookRequestDto request) {
        BookResponseDto response = bookService.updateBook(id, request);
        ResponseDto responseDto = new ResponseDto("도서 정보가 수정되었습니다.", response);
        return ResponseEntity.ok(responseDto);
    }

    // 도서 삭제 (soft delete)
    @DeleteMapping("/{bookId}")
    public ResponseEntity<ResponseDto> deleteBook(@PathVariable("bookId") Long id) {
        bookService.deleteBook(id);
        ResponseDto responseDto = new ResponseDto("도서가 삭제되었습니다.", null);
        return ResponseEntity.ok(responseDto);
    }
}