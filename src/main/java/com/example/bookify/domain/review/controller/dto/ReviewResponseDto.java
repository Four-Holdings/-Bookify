package com.example.bookify.domain.review.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponseDto {

    private Long reviewId;
    private Long userId;
    private String nickname;
    private int grades;
    private String content;


}
