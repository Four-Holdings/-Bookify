package com.example.bookify.domain.keyword.controller;

import com.example.bookify.domain.keyword.controller.dto.KeywordListResponse;
import com.example.bookify.domain.keyword.service.KeywordService;
import com.example.bookify.domain.keyword.service.dto.KeywordResponse;
import com.example.bookify.global.common.apiresponse.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordService keywordService;

    @GetMapping("/keywords/popular")
    public ResponseEntity<ResponseDto<KeywordListResponse>> getPopularKeywords(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        size = Math.min(Math.max(size, 1), 100);

        Page<KeywordResponse> keywordPage = keywordService.getTopKeywords(page, size);
        KeywordListResponse keywordListResponse = new KeywordListResponse(keywordPage.getContent());

        return ResponseEntity.ok(
                new ResponseDto<>("인기 검색어 조회 성공", keywordListResponse)
        );
    }
}
