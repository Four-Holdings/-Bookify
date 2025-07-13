package com.example.bookify.domain.keyword.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class KeywordCacheService {

    //TTL = Time to live
    private static final long CACHE_TTL_MINUTES = 10;
    private static final long CACHE_MAX_SIZE = 10_000;

    private Cache<String, Long> keywordCache;

    @PostConstruct
    public void init() {
        this.keywordCache = Caffeine.newBuilder()
                .expireAfterAccess(CACHE_TTL_MINUTES, TimeUnit.MINUTES)
                .maximumSize(CACHE_MAX_SIZE)
                .build();
    }

    public void saveOrIncreaseKeyword(String keyword) {
        keywordCache.asMap().merge(keyword, 1L, Long::sum);
    }

    public void clearAll() {
        keywordCache.invalidateAll();
    }

    public Map<String, Long> getAllKeywordsWithCount() {
        return new HashMap<>(keywordCache.asMap());
    }
}
