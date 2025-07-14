package com.example.bookify.domain.review.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponseDto {

    private Long reviewId;
    private Long userId;
    // nickname -> name
    private String name;
    private int grades;
    private String content;


}
