package com.example.bookify.domain.keyword.service;

import io.lettuce.core.ScoredValue;
import io.lettuce.core.api.async.RedisAsyncCommands;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class RedisKeywordCacheService {

    private final RedisAsyncCommands<String, String> redisAsyncCommands;
    private static final String REDIS_KEY = "search:keywords";

    public void saveOrIncreaseKeyword(String keyword) {
        redisAsyncCommands.zincrby(REDIS_KEY, 1, keyword);
    }

    public CompletableFuture<Map<String, Long>> getAllKeywordsWithCount() {
        return redisAsyncCommands.zrevrangeWithScores(REDIS_KEY, 0, -1)
                .toCompletableFuture()
                .thenApply(keywordList -> {
                    Map<String, Long> result = new HashMap<>();
                    for (ScoredValue<String> value : keywordList) {
                        result.put(value.getValue(), (long) value.getScore());
                    }
                    return result;
                });
    }

    public void clearAll() {
        redisAsyncCommands.del(REDIS_KEY);
    }
}
