package com.example.bookify.domain.statistics.service;

import com.example.bookify.domain.book.domain.repository.BookRepository;
import com.example.bookify.domain.statistics.controller.dto.TopRatingBooksResponseDto;
import com.example.bookify.domain.statistics.domain.model.Statistics;
import com.example.bookify.domain.statistics.domain.repository.StatisticsRepository;
import com.example.bookify.domain.statistics.service.dto.AvgReviewGradeDto;
import com.example.bookify.global.common.exception.exceptionclass.CustomException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static com.example.bookify.global.common.exception.enums.ExceptionCode.NOT_FOUND_PAGE;

@Slf4j
@Service
public class StatisticsService {


    private final StatisticsRepository statisticsRepository;

    public StatisticsService(StatisticsRepository statisticsRepository, BookRepository bookRepository, EntityManager entityManager, EntityManagerFactory entityManager1) {
        this.statisticsRepository = statisticsRepository;
    }

    @Transactional
    @Scheduled(fixedDelay = 300000)
    public void bookRatingStatistics() {
        // 현재 월의 시작일 0시 0분 0초
        LocalDateTime startDate = YearMonth.now().atDay(1).atStartOfDay();

        // 내달 시작일 0시 0분 0초
        LocalDateTime endDate = YearMonth.now().atEndOfMonth().plusDays(1).atStartOfDay();

        // 현재 월의 통계 삭제
        statisticsRepository.deleteByStatistics(startDate, endDate);

        // 현재 월에 생성, 수정리뷰 평점 평균 순위
        List<AvgReviewGradeDto> avgGradesReview = statisticsRepository.avgByReviewByGrades(startDate, endDate);

        if (avgGradesReview == null || avgGradesReview.isEmpty()) {
            return;
        }

        // 순위 지정 및 DB저장 로직
        List<Statistics> save = new ArrayList<>();

        Long reviewRank = 1L;
        for (AvgReviewGradeDto gradeRank : avgGradesReview) {
            Statistics ratingRanking = new Statistics(
                    reviewRank++,
                    gradeRank.getAvgGrade(),
                    gradeRank.getCountReview(),
                    gradeRank.getBook()
            );
            save.add(ratingRanking);
        }
        statisticsRepository.saveAll(save);
    }

    @Transactional(readOnly = true)
    public TopRatingBooksResponseDto findTopRating(YearMonth yearMonth, Pageable pageable) {
        LocalDateTime startDate = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = yearMonth.atEndOfMonth().plusDays(1).atStartOfDay();

        Pageable TopRatingPage = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        Page<Statistics> responsePage = statisticsRepository.findTopRating(startDate, endDate, TopRatingPage);

        if (responsePage == null || responsePage.isEmpty()) {
            throw new CustomException(NOT_FOUND_PAGE);
        }

        return TopRatingBooksResponseDto.formPage(responsePage);
    }


}
