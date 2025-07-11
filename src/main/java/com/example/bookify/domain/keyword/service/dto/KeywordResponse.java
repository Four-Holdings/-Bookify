package com.example.bookify.domain.keyword.service.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"keyword", "count"})
public class KeywordResponse {

    private String keyword;
    @PositiveOrZero(message = "카운트는 0 이상이어야 합니다.")
    private long count;
}
