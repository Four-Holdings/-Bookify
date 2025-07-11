package com.example.bookify.global.aop;

import com.example.bookify.domain.keyword.service.KeywordService;
import com.example.bookify.global.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SearchKeywordCollectorAspect {

    private final KeywordService keywordService;

    @Pointcut("@annotation(com.example.bookify.global.aop.annotation.CollectSearchKeyword)")
    public void collectSearchKeywordPointcut() {

    }

    @Before("collectSearchKeywordPointcut()")
    public void collectKeyword(JoinPoint joinPoint) {
        try {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) return;

            HttpServletRequest request = attributes.getRequest();

            String keyword = request.getParameter("keyword");

            if (keyword != null && !keyword.isBlank()) {
                String cleanedKeyword = KeywordUtils.cleanKeyword(keyword);
                log.info("검색어 수집 cleanedKeyword = {}", cleanedKeyword);

                keywordService.saveKeyword(keyword);

            } else {
                log.info("파라미터가 없거나 빈문자열 입니다.");
            }
        } catch (Exception e) {
            log.warn("검색어 수집 중 예외 발생: {}",e.getMessage());
        }
    }
}
