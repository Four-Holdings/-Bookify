package com.example.bookify.domain.keyword.service.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"word", "count"})
public class KeywordResponse {
    private String word;
    private long count;
}
