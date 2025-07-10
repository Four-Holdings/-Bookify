package com.example.bookify.domain.review.controller;


import com.example.bookify.domain.review.controller.dto.ReviewRequestDto;
import com.example.bookify.domain.review.controller.dto.ReviewResponseDto;
import com.example.bookify.domain.review.service.ReviewService;
import com.example.bookify.domain.user.domain.model.User;
import com.example.bookify.domain.user.domain.repository.UserRepository;
import com.example.bookify.global.common.exception.enums.ExceptionCode;
import com.example.bookify.global.common.exception.exceptionclass.CustomException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public Map<String, Object> createReview(@RequestBody ReviewRequestDto requestDto)
    {

        //JWT 없어서 임시처리
        String userEmail = "test@example.com"; // 더미 이메일

        ReviewResponseDto reviewResponseDto = reviewService.createReview(requestDto, userEmail);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "리뷰가 등록되었습니다.");
        response.put("data", reviewResponseDto);
        response.put("timestamp", ZonedDateTime.now());

        return response;
    }

    @GetMapping("/books/{bookId}/reviews")
    public Map<String, Object> getReviewsByBookId(@PathVariable Long bookId) {
        List<ReviewResponseDto> reviewList = reviewService.getReviewsByBookId(bookId);

        Map<String, Object> data = new HashMap<>();
        data.put("total", reviewList.size());
        data.put("reviews", reviewList);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message" , "리뷰 목록 조회 성공");
        response.put("data", reviewList );
        response.put("timestamp", ZonedDateTime.now());

        return response;

    }

    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> updateReview(@PathVariable Long reviewId,
                                             @RequestBody @Valid ReviewRequestDto requestDto) {
        String userEmail = "test@example.com";
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        reviewService.updateReview(reviewId, requestDto, user);

        return ResponseEntity.ok() .build();
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        String userEmail = "test@example.com";
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        reviewService.deleteReview(reviewId, user);
        return ResponseEntity.noContent().build();
    }
}
