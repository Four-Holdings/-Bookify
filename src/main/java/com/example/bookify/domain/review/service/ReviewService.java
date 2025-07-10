package com.example.bookify.domain.review.service;

import com.example.bookify.domain.book.entity.Book;
import com.example.bookify.domain.book.repository.BookRepository;
import com.example.bookify.domain.review.controller.dto.ReviewRequestDto;
import com.example.bookify.domain.review.controller.dto.ReviewResponseDto;
import com.example.bookify.domain.review.domain.model.Review;
import com.example.bookify.domain.review.domain.repository.ReviewRepository;
import com.example.bookify.domain.user.domain.model.User;
import com.example.bookify.domain.user.domain.repository.UserRepository;
import com.example.bookify.global.common.exception.enums.ExceptionCode;
import com.example.bookify.global.common.exception.exceptionclass.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReviewResponseDto createReview(ReviewRequestDto reviewRequestDto, String userEmail) {
        Book book = bookRepository.findById(reviewRequestDto.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 책입니다."));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("유저 정보를 찾을 수 없습니다."));

        Review review = new Review(
                null,
                user,
                book,
                reviewRequestDto.getGrades(),
                reviewRequestDto.getContent()
        );

        Review savedReview = reviewRepository.save(review);

        return ReviewResponseDto.builder()
                .reviewId(savedReview.getId())
                .content(savedReview.getContent())
                .grades(savedReview.getGrades())
                .build();
    }

    public List<ReviewResponseDto> getReviewsByBookId(Long bookId) {
        return reviewRepository.findBybookIdAndIsDeletedFalse(bookId)
                .stream()
                .map(review -> ReviewResponseDto.builder()
                        .reviewId(review.getId())
                        .userId(review.getUser().getId())
                        .nickname(review.getUser().getNickname())
                        .content(review.getContent())
                        .grades(review.getGrades())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateReview(Long reviewId, ReviewRequestDto requestDto, User user) {
        Review review = reviewRepository.findByIdAndIsDeletedFalse(reviewId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_REVIEW));


        System.out.println(" review.getUser().getId(): " + review.getUser().getId());
        System.out.println(" user.getId(): " + user.getId());
        System.out.println(" review == user: " + (review.getUser() == user));
        System.out.println(" review.getUser().equals(user): " + review.getUser().equals(user));

        if (!Objects.equals(review.getUser().getId(), user.getId())) {
            throw new CustomException(ExceptionCode.FORBIDDEN_UPDATE_REVIEW);
        }

        review.updateReview(requestDto.getGrades(), requestDto.getContent());

    }

    @Transactional
    public void deleteReview(Long reviewId, User user) {
        Review review = reviewRepository.findByIdAndIsDeletedFalse(reviewId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_REVIEW));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new CustomException(ExceptionCode.FORBIDDEN_DELETE_REVIEW);
        }

        review.delete();
    }

}
