package com.example.bookify.domain.review.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {
    //리뷰 생성 및 수정 dto

    @NotNull
    private Long bookId;

    @Min(1)
    @Max(5)
    private int grades;

    @NotBlank
    private String content;

}
