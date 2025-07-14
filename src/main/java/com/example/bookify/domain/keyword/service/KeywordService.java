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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;
    private final KeywordCacheService keywordCacheService;
    private final RedisKeywordCacheService redisKeywordCacheService;

    /**
     * DB 검색어 저장 및 DB에 중복시 count +1
     */
    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveKeyword(String keyword) {
        keywordRepository.findByKeyword(keyword).ifPresentOrElse(Keyword::addCount, () -> {
            Keyword newKeyword = Keyword.createKeyword(keyword);
            keywordRepository.save(newKeyword);
        });
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

    //caffeine 캐시 적용 V2

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveKeywordV2(String keyword) {

        keywordCacheService.saveOrIncreaseKeyword(keyword);

    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveKeywordV3(String keyword) {

        redisKeywordCacheService.saveOrIncreaseKeyword(keyword);
    }

    /**
     * 배치에서 주기적으로 호출: 캐시 -> DB 반영
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void syncCaffeineToDB() {
        Map<String, Long> keywordMap = keywordCacheService.getAllKeywordsWithCount();
        upsertKeywords(keywordMap);
        keywordCacheService.clearAll();
    }


    /**
     * (Redis 캐시 적용 버전) 주기적으로 호출: Redis 캐시 데이터 -> DB반영
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void syncRedisToDB() {
        redisKeywordCacheService.getAllKeywordsWithCount()
                .thenAccept(keywordMap -> {
                    upsertKeywords(keywordMap);
                    redisKeywordCacheService.clearAll();
                })
                .exceptionally(ex -> {
                    // 예외 로깅
                    return null;
                });
    }

    /**
     * 공통: 키워드가 있으면 count 증가, 없으면 새로 생성 후 저장
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void upsertKeywords(Map<String, Long> keywordCountMap) {

        if (keywordCountMap == null || keywordCountMap.isEmpty()) {
            return;
        }

        //캐시로 받아온 정보 리스트
        List<String> keywords = new ArrayList<>(keywordCountMap.keySet());

        // 1) DB에서 이미 존재하는 키워드 한꺼번에 조회
        List<Keyword> existingKeywords = keywordRepository.findByKeywordIn(keywords);

        // 2) 존재하는 키워드 맵으로 변환
        Map<String, Keyword> existingKeywordMap = existingKeywords.stream()
                .collect(Collectors.toMap(Keyword::getKeyword, Function.identity()));

        //새로 생성한 객체 리스트
        List<Keyword> keywordsToInsert = new ArrayList<>();

        // 3) 각 키워드에 대해 upsert 로직 실행
        keywordCountMap.forEach((keyword, count) -> {
            Optional.ofNullable(existingKeywordMap.get(keyword))
                    .ifPresentOrElse(
                            entity -> entity.incrementCountBy(count),  // 기존 키워드: count 증가
                            () -> keywordsToInsert.add(Keyword.createKeywordWithCount(keyword,count))  // 새 키워드: 삽입 리스트에 추가
                    );
        });

        if (!keywordsToInsert.isEmpty()) {
            keywordRepository.saveAll(keywordsToInsert);
        }
    }
}

