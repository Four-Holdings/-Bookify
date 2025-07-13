package com.example.bookify.global.scheduler;

import com.example.bookify.domain.keyword.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KeywordSyncScheduler {

    private final KeywordService keywordService;

    private static final long CAFFEINE_SYNC_DELAY_MS = 30_0000;

    private static final long REDIS_SYNC_DELAY_MS = 6_0000;

//    @Scheduled(fixedDelay = CAFFEINE_SYNC_DELAY_MS)
//    public void syncCaffeineToDB() {
//        keywordService.syncCaffeineToDB();
//    }

    @Scheduled(fixedDelay = REDIS_SYNC_DELAY_MS)
    public void syncRedisToDB(){
        keywordService.syncRedisToDB();
    }
}
