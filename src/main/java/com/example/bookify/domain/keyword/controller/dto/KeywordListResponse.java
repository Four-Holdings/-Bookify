package com.example.bookify.domain.keyword.controller.dto;

import com.example.bookify.domain.keyword.service.dto.KeywordResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class KeywordListResponse {
    private List<KeywordResponse> keywords;
}
