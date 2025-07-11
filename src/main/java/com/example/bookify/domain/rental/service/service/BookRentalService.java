package com.example.bookify.domain.rental.service.service;

import com.example.bookify.domain.book.entity.Book;
import com.example.bookify.domain.book.repository.BookRepository;
import com.example.bookify.domain.rental.domain.model.BookRental;
import com.example.bookify.domain.rental.domain.model.RentalStatus;
import com.example.bookify.domain.rental.domain.repository.BookRentalRepository;
import com.example.bookify.domain.rental.service.dto.BookRentalResponseDto;
import com.example.bookify.domain.user.domain.model.User;
import com.example.bookify.domain.user.domain.repository.UserRepository;
import com.example.bookify.global.common.exception.enums.ExceptionCode;
import com.example.bookify.global.common.exception.exceptionclass.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


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
        BookRental rental = new BookRental(
                null,
                book,
                user,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                null,
                RentalStatus.RENTED
        );

        bookRentalRepository.save(rental);
        return BookRentalResponseDto.from(rental);
    }
    //2.도서 반납 처리



}
