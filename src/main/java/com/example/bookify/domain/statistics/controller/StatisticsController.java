package com.example.bookify.domain.statistics.controller;

import com.example.bookify.domain.statistics.controller.dto.TopRatingBooksResponseDto;
import com.example.bookify.domain.statistics.service.StatisticsService;
import com.example.bookify.global.common.apiresponse.ResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/books/ranking")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/rating")
    public ResponseEntity<ResponseDto<TopRatingBooksResponseDto>> topRatingBooks(
            @PageableDefault Pageable pageable,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM") String yearMonth) {

        YearMonth parseYearMonth = YearMonth.parse(yearMonth);

        TopRatingBooksResponseDto topRating = statisticsService.findTopRating(parseYearMonth, pageable);

        ResponseDto<TopRatingBooksResponseDto> responseDto = new ResponseDto<>("베스트셀러", topRating);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
