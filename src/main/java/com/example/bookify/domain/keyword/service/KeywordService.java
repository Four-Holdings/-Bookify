package com.example.bookify.domain.keyword.service;

import com.example.bookify.domain.keyword.domain.model.Keyword;
import com.example.bookify.domain.keyword.domain.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    @Transactional
    public void saveKeyword(String keyword) {
        keywordRepository.findByKeyword(keyword)
                .ifPresentOrElse(
                        existingKeyword -> existingKeyword.addCount(),
                        () -> {
                            Keyword newKeyword = Keyword.createKeyword(keyword);
                            keywordRepository.save(newKeyword);
                        }
                );
    }

    /**
     * 인기 검색어 상위 N개 조회
     */

    @Transactional(readOnly = true)
    public List<Keyword> getTopKeywords(int limit) {
        return keywordRepository.findTopKeywords(PageRequest.of(0, limit));
    }

    /**
     * 키워드 존재 여부 빠르게 확인
     */

    public boolean existsKeyword(String keyword) {
        return keywordRepository.existsByKeyword(keyword);
    }
}
