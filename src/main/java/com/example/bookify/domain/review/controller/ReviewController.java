package com.example.bookify.domain.review.controller;

import com.example.bookify.domain.review.controller.dto.ReviewRequestDto;
import com.example.bookify.domain.review.controller.dto.ReviewResponseDto;
import com.example.bookify.domain.review.service.ReviewService;
import com.example.bookify.domain.user.domain.model.User;
import com.example.bookify.domain.user.domain.repository.UserRepository;
import com.example.bookify.global.common.exception.enums.ExceptionCode;
import com.example.bookify.global.common.exception.exceptionclass.CustomException;
import com.example.bookify.global.security.jwt.CustomPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserRepository userRepository;

    // 리뷰 생성
    @PostMapping("/reviews")
    public Map<String, Object> createReview(@AuthenticationPrincipal CustomPrincipal customPrincipal,
                                            @RequestBody @Valid ReviewRequestDto requestDto)
    {
        ReviewResponseDto reviewResponseDto = reviewService.createReview(requestDto, customPrincipal.getUserId());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "리뷰가 등록되었습니다.");
        response.put("data", reviewResponseDto);
        response.put("timestamp", ZonedDateTime.now());

        return response;
    }

    @GetMapping("/books/{bookId}/reviews")
    public Map<String, Object> getReviewsByBookId(@PathVariable Long bookId) {
        List<ReviewResponseDto> reviewList = reviewService.getReviewsByBookId(bookId);


        Map<String, Object> response = new HashMap<>();
        response.put("message" , "리뷰 목록 조회 성공");
        response.put("data", reviewList );
        response.put("timestamp", ZonedDateTime.now());

        return response;

    }

    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> updateReview(@AuthenticationPrincipal CustomPrincipal customPrincipal,
                                             @PathVariable Long reviewId,
                                             @RequestBody @Valid ReviewRequestDto requestDto) {

        reviewService.updateReview(reviewId, requestDto, customPrincipal.getUserId());

        return ResponseEntity.ok() .build();
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@AuthenticationPrincipal CustomPrincipal  customPrincipal,
                                             @PathVariable Long reviewId) {

        reviewService.deleteReview(reviewId, customPrincipal.getUserId());
        return ResponseEntity.noContent().build();
    }
}
