package com.example.bookify.domain.review.service;

import com.example.bookify.domain.book.domain.model.Book;
import com.example.bookify.domain.book.domain.repository.BookRepository;
import com.example.bookify.domain.review.controller.dto.ReviewRequestDto;
import com.example.bookify.domain.review.controller.dto.ReviewResponseDto;
import com.example.bookify.domain.review.domain.model.Review;
import com.example.bookify.domain.review.domain.repository.ReviewRepository;
import com.example.bookify.domain.user.domain.model.User;
import com.example.bookify.domain.user.domain.repository.UserRepository;
import com.example.bookify.global.common.exception.enums.ExceptionCode;
import com.example.bookify.global.common.exception.exceptionclass.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReviewResponseDto createReview(ReviewRequestDto reviewRequestDto, Long userId) {
        Book book = bookRepository.findById(reviewRequestDto.getBookId())
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_BOOK));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        Review review = new Review(
                user,
                book,
                reviewRequestDto.getGrades(),
                reviewRequestDto.getContent()
        );

        Review savedReview = reviewRepository.save(review);

        // builer는 값 순서가 상관없음, 값 섞이는 거 방지 가능, 가독성 향상
        return ReviewResponseDto.builder()
                .reviewId(savedReview.getId())
                .userId(user.getId())
                .name(user.getName())
                .content(savedReview.getContent())
                .grades(savedReview.getGrades())
                .build();
    }

    @Transactional
    public Page<ReviewResponseDto> getReviewsByBookId(Long bookId, Pageable pageable) {
        return reviewRepository.findBybookIdAndIsDeletedFalse(bookId, pageable)
                .map(review -> ReviewResponseDto.builder()
                        .reviewId(review.getId())
                        .userId(review.getUser().getId())
                        .name(review.getUser().getName())
                        .content(review.getContent())
                        .grades(review.getGrades())
                        .build());
    }

    @Transactional
    public void updateReview(Long reviewId, ReviewRequestDto requestDto, Long userId) {
        Review review = reviewRepository.findByIdAndIsDeletedFalse(reviewId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_REVIEW));


        if (!review.getUser().getId().equals(userId)) {
            throw new CustomException(ExceptionCode.FORBIDDEN_UPDATE_REVIEW);
        }

        review.updateReview(requestDto.getGrades(), requestDto.getContent());

    }

    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findByIdAndIsDeletedFalse(reviewId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_REVIEW));

        if (!review.getUser().getId().equals(userId)) {
            throw new CustomException(ExceptionCode.FORBIDDEN_DELETE_REVIEW);
        }

        review.delete();
    }

}
