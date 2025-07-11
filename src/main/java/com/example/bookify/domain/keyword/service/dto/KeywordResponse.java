package com.example.bookify.domain.keyword.service.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"keyword", "count"})
public class KeywordResponse {

    private String keyword;

    private long count;
}
