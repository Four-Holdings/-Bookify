package com.example.bookify.domain.rental.service.service;


import com.example.bookify.domain.book.domain.model.Book;
import com.example.bookify.domain.book.domain.repository.BookRepository;
import com.example.bookify.domain.rental.domain.model.BookRental;
import com.example.bookify.domain.rental.domain.model.RentalStatus;
import com.example.bookify.domain.rental.domain.repository.BookRentalRepository;
import com.example.bookify.domain.rental.service.dto.BookRentalResponseDto;
import com.example.bookify.domain.rental.service.dto.BookRentalStatusDto;
import com.example.bookify.domain.user.domain.model.User;
import com.example.bookify.domain.user.domain.repository.UserRepository;
import com.example.bookify.global.common.exception.enums.ExceptionCode;
import com.example.bookify.global.common.exception.exceptionclass.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BookRentalService {

    private final BookRentalRepository bookRentalRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    //1. 도서 대여 처리
    @Transactional
    public BookRentalResponseDto rentBook(Long bookId, Long userId) {
        if (bookRentalRepository.existsByBookIdAndStatus(bookId, RentalStatus.RENTED)) {
            // "이미 대여중인 도서입니다" 예외처리
            throw new CustomException(ExceptionCode.RENTED_BOOK);
        }

        // "해당 도서는 찾을수 없습니다" 예외 처리
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_BOOK));
        //"로그인이 필요 합니다" 예외 처리
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionCode.LOGIN_REQUIRED));
        // BookRental 객체 만들어 bookRentalRepository.save()에 있는 매개변수에 주입
        LocalDateTime now = LocalDateTime.now();

        BookRental rental = BookRental.builder()
                .book(book)
                .user(user)
                .dueAt(now.plusDays(14))
                .rentedAt(LocalDateTime.now())
                .status(RentalStatus.RENTED)
                .build();
        bookRentalRepository.save(rental);

        return BookRentalResponseDto.fromEntity(rental);
    }

    //2.도서 반납 처리
    @Transactional
    public BookRentalResponseDto returnBook(Long rentalId, Long userId) {
        BookRental rental = bookRentalRepository.findById(rentalId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_BOOK));

        if (!rental.getUser().getId().equals(userId)) {
            throw new CustomException(ExceptionCode.FORBIDDEN_RETURN_BOOK);
        }

        if (rental.getStatus() == RentalStatus.RETURNED) {
            throw new CustomException(ExceptionCode.ALREADY_RETURNED);
        }

        rental.returnBook();
        return BookRentalResponseDto.fromEntity(rental);
    }

    //3. 도서 상태 조회
    public BookRentalStatusDto getRentalStatus(Long bookId) {
        boolean isRented = bookRentalRepository.existsByBookIdAndStatus(bookId, RentalStatus.RENTED);
        return new BookRentalStatusDto(bookId, isRented);
    }

    //4.사용자 도서 대여 이력 조회
    public List<BookRentalResponseDto> getMyRentalHistory(Long userId) {
        return bookRentalRepository.findByUserId(userId).stream()
                .map(BookRentalResponseDto::fromEntity)
                .collect(Collectors.toList());
    }


}
