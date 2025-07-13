package com.example.bookify.domain.review.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {
    //리뷰 생성 및 수정 dto

    @NotNull
    private Long bookId;

    @Range(min=1, max=5)
    private int grades;

    @NotBlank
    private String content;

}
