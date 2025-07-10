package com.example.bookify.domain.keyword.service.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"keyword", "count"})
public class KeywordResponse {

    @NotBlank(message = "키워드는 빈 값일 수 없습니다.")
    private String keyword;
    @PositiveOrZero(message = "카운트는 0 이상이어야 합니다.")
    private long count;
}
