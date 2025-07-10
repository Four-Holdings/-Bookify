package com.example.bookify.domain.keyword.service;

import com.example.bookify.domain.keyword.domain.model.Keyword;
import com.example.bookify.domain.keyword.domain.repository.KeywordRepository;
import com.example.bookify.domain.keyword.service.dto.KeywordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    /**
     * DB 검색어 저장 및 DB에 중복시 count +1
     */
    @Async
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
    public Page<KeywordResponse> getTopKeywords(int page, int size) {

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Keyword> keywordPage = keywordRepository.findTopKeywords(pageable);

        return keywordPage.map(keyword -> new KeywordResponse(keyword.getKeyword(), keyword.getCount()));
    }


    /**
     * 키워드 존재 여부 빠르게 확인
     */

    public boolean existsKeyword(String keyword) {
        return keywordRepository.existsByKeyword(keyword);
    }
}
