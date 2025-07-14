package com.example.bookify.domain.review.controller;

import com.example.bookify.domain.review.controller.dto.ReviewRequestDto;
import com.example.bookify.domain.review.controller.dto.ReviewResponseDto;
import com.example.bookify.domain.review.service.ReviewService;
import com.example.bookify.global.common.apiresponse.PagedResponseDto;
import com.example.bookify.global.common.apiresponse.ResponseDto;
import com.example.bookify.global.security.jwt.CustomPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping("/reviews")
    public ResponseEntity<ResponseDto<ReviewResponseDto>> createReview(
            @AuthenticationPrincipal CustomPrincipal customPrincipal,
            @RequestBody @Valid ReviewRequestDto requestDto
    ) {
        ReviewResponseDto responseDto = reviewService.createReview(requestDto, customPrincipal.getUserId());
        return ResponseEntity.ok(new ResponseDto<>("리뷰가 등록되었습니다.", responseDto));
    }



    @GetMapping("/books/{bookId}/reviews")
    public ResponseEntity<ResponseDto<PagedResponseDto<ReviewResponseDto>>> getReviewsByBookId(@PathVariable Long bookId,
                                                                                               @RequestParam(defaultValue = "0") int page,
                                                                                               @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ReviewResponseDto> reviewPage = reviewService.getReviewsByBookId(bookId, pageable);

        PagedResponseDto<ReviewResponseDto> pagedResponse = PagedResponseDto.of(
                reviewPage.getContent(),
                reviewPage.getNumber(),
                reviewPage.getTotalPages(),
                reviewPage.getTotalElements(),
                reviewPage.getSize(),
                reviewPage.hasNext(),
                reviewPage.hasPrevious()

        );


        return ResponseEntity.ok(
                new ResponseDto<>("리뷰 목록 조회 성공", pagedResponse)
        );

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
